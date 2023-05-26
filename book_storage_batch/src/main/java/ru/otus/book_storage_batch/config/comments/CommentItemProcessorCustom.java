package ru.otus.book_storage_batch.config.comments;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.otus.book_storage_batch.dto.comments.CommentInputDto;
import ru.otus.book_storage_batch.dto.comments.CommentOutputDto;
import ru.otus.book_storage_batch.utils.ObjectIdUtils;

@Configuration
public class CommentItemProcessorCustom {


    @Bean
    public ItemProcessor<CommentInputDto, CommentOutputDto> commentProcessor() {
        return new CommentItemProcessor();
    }

    private static class CommentItemProcessor implements ItemProcessor<CommentInputDto, CommentOutputDto> {

        @Override
        public CommentOutputDto process(CommentInputDto item) {
            CommentOutputDto commentOutputDto = new CommentOutputDto();
            commentOutputDto.setId(ObjectIdUtils.getObjectId());
            commentOutputDto.setText(item.getText());
            return commentOutputDto;
        }
    }
}
