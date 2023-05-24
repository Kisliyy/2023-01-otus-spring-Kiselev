package ru.otus.book_storage_batch.config.authors;

import org.springframework.batch.item.data.MongoItemWriter;
import org.springframework.batch.item.data.builder.MongoItemWriterBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import ru.otus.book_storage_batch.dto.authors.AuthorOutputDto;

@Configuration
public class AuthorWriter {

    @Bean
    public MongoItemWriter<AuthorOutputDto> authorItemWriter(MongoTemplate mongoTemplate) {
        return new MongoItemWriterBuilder<AuthorOutputDto>()
                .template(mongoTemplate)
                .collection("authors")
                .delete(false)
                .build();
    }

}
