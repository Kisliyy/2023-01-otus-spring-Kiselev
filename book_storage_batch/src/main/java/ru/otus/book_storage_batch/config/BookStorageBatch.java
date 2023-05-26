package ru.otus.book_storage_batch.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.FlowBuilder;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.batch.core.job.flow.support.SimpleFlow;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.data.MongoItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;
import org.springframework.transaction.PlatformTransactionManager;
import ru.otus.book_storage_batch.config.listeners.AuthorCustomProcessListener;
import ru.otus.book_storage_batch.config.listeners.GenreCustomProcessListener;
import ru.otus.book_storage_batch.dto.authors.AuthorInputDto;
import ru.otus.book_storage_batch.dto.authors.AuthorOutputDto;
import ru.otus.book_storage_batch.dto.books.BookInputDto;
import ru.otus.book_storage_batch.dto.books.BookOutputDto;
import ru.otus.book_storage_batch.dto.comments.CommentInputDto;
import ru.otus.book_storage_batch.dto.comments.CommentOutputDto;
import ru.otus.book_storage_batch.dto.genres.GenreInputDto;
import ru.otus.book_storage_batch.dto.genres.GenreOutputDto;

@Configuration
public class BookStorageBatch {

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private PlatformTransactionManager platformTransactionManager;

    @Autowired
    private AuthorCustomProcessListener authorCustomProcessListener;

    @Autowired
    private GenreCustomProcessListener genreCustomProcessListener;


    @Bean
    public Job importBookStorageJob(Step transformAuthorsStep,
                                    Step transformGenresStep,
                                    Step transformCommentsStep,
                                    Step transformBooksStep) {
        return new JobBuilder("importAuthorsJob")
                .repository(jobRepository)
                .incrementer(new RunIdIncrementer())
                .start(splitAuthorsGenresFlow(transformAuthorsStep, transformGenresStep))
                .next(transformCommentsStep)
                .next(transformBooksStep)
                .end()
                .build();
    }


    @Bean
    public Step transformAuthorsStep(ItemReader<AuthorInputDto> reader, MongoItemWriter<AuthorOutputDto> writer,
                                     ItemProcessor<AuthorInputDto, AuthorOutputDto> itemProcessor) {
        return new StepBuilder("transformAuthorsStep")
                .repository(jobRepository)
                .transactionManager(platformTransactionManager)
                .<AuthorInputDto, AuthorOutputDto>chunk(10)
                .reader(reader)
                .processor(itemProcessor)
                .listener(authorCustomProcessListener)
                .writer(writer)
                .build();
    }

    @Bean
    public Step transformBooksStep(ItemReader<BookInputDto> reader, MongoItemWriter<BookOutputDto> writer,
                                   ItemProcessor<BookInputDto, BookOutputDto> itemProcessor) {
        return new StepBuilder("transformBooksStep")
                .repository(jobRepository)
                .transactionManager(platformTransactionManager)
                .<BookInputDto, BookOutputDto>chunk(10)
                .reader(reader)
                .processor(itemProcessor)
                .writer(writer)
                .build();
    }


    @Bean
    public Step transformGenresStep(ItemReader<GenreInputDto> reader, MongoItemWriter<GenreOutputDto> writer,
                                    ItemProcessor<GenreInputDto, GenreOutputDto> itemProcessor) {
        return new StepBuilder("transformGenresStep")
                .repository(jobRepository)
                .transactionManager(platformTransactionManager)
                .<GenreInputDto, GenreOutputDto>chunk(10)
                .reader(reader)
                .listener(genreCustomProcessListener)
                .processor(itemProcessor)
                .writer(writer)
                .build();
    }

    @Bean
    public Step transformCommentsStep(ItemReader<CommentInputDto> reader, MongoItemWriter<CommentOutputDto> writer,
                                      ItemProcessor<CommentInputDto, CommentOutputDto> itemProcessor) {
        return new StepBuilder("transformCommentsStep")
                .repository(jobRepository)
                .transactionManager(platformTransactionManager)
                .<CommentInputDto, CommentOutputDto>chunk(10)
                .reader(reader)
                .listener(genreCustomProcessListener)
                .processor(itemProcessor)
                .writer(writer)
                .build();
    }

    @Bean
    public Flow splitAuthorsGenresFlow(Step transformAuthorsStep, Step transformGenresStep) {
        return new FlowBuilder<SimpleFlow>("splitAuthorsGenresFlow")
                .split(taskExecutor())
                .add(authorsFlow(transformAuthorsStep), genresFlow(transformGenresStep))
                .build();
    }

    @Bean
    public Flow authorsFlow(Step transformAuthorsStep) {
        return new FlowBuilder<SimpleFlow>("authorsFlow")
                .start(transformAuthorsStep)
                .build();
    }

    @Bean
    public Flow genresFlow(Step transformGenresStep) {
        return new FlowBuilder<SimpleFlow>("genresFlow")
                .start(transformGenresStep)
                .build();
    }

    @Bean
    public TaskExecutor taskExecutor() {
        return new SimpleAsyncTaskExecutor("parallel_batch");
    }
}
