package ru.otus.book_storage_batch.config.comments;

import org.springframework.batch.item.data.MongoItemWriter;
import org.springframework.batch.item.data.builder.MongoItemWriterBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import ru.otus.book_storage_batch.dto.comments.CommentOutputDto;

@Configuration
public class CommentWriter {

    @Bean
    public MongoItemWriter<CommentOutputDto> commentItemWriter(MongoTemplate mongoTemplate) {
        return new MongoItemWriterBuilder<CommentOutputDto>()
                .template(mongoTemplate)
                .collection("comments")
                .delete(false)
                .build();
    }

}
