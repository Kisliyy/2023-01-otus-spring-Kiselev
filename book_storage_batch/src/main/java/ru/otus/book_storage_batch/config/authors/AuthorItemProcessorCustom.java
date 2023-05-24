package ru.otus.book_storage_batch.config.authors;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.otus.book_storage_batch.dto.authors.AuthorInputDto;
import ru.otus.book_storage_batch.dto.authors.AuthorOutputDto;
import ru.otus.book_storage_batch.utils.ObjectIdUtils;

@Configuration
public class AuthorItemProcessorCustom {


    @Bean
    public ItemProcessor<AuthorInputDto, AuthorOutputDto> authorProcessor() {
        return new AuthorItemProcessor();
    }

    private static class AuthorItemProcessor implements ItemProcessor<AuthorInputDto, AuthorOutputDto> {

        @Override
        public AuthorOutputDto process(AuthorInputDto item) {
            AuthorOutputDto authorOutputDto = new AuthorOutputDto();
            authorOutputDto.setId(ObjectIdUtils.getObjectId());
            authorOutputDto.setFirstName(item.getFirstName());
            authorOutputDto.setLastName(item.getLastName());
            return authorOutputDto;
        }
    }
}
