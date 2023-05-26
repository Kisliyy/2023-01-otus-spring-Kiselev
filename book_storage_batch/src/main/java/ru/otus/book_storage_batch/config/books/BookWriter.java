package ru.otus.book_storage_batch.config.books;

import org.springframework.batch.item.data.MongoItemWriter;
import org.springframework.batch.item.data.builder.MongoItemWriterBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import ru.otus.book_storage_batch.dto.books.BookOutputDto;

@Configuration
public class BookWriter {

    @Bean
    public MongoItemWriter<BookOutputDto> bookItemWriter(MongoTemplate mongoTemplate) {
        return new MongoItemWriterBuilder<BookOutputDto>()
                .template(mongoTemplate)
                .collection("books")
                .delete(false)
                .build();
    }
}
