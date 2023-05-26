package ru.otus.book_storage_batch.config.genres;

import org.springframework.batch.item.data.MongoItemWriter;
import org.springframework.batch.item.data.builder.MongoItemWriterBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import ru.otus.book_storage_batch.dto.genres.GenreOutputDto;

@Configuration
public class GenreWriter {

    @Bean
    public MongoItemWriter<GenreOutputDto> genreItemWriter(MongoTemplate mongoTemplate) {
        return new MongoItemWriterBuilder<GenreOutputDto>()
                .template(mongoTemplate)
                .collection("genres")
                .delete(false)
                .build();
    }

}
