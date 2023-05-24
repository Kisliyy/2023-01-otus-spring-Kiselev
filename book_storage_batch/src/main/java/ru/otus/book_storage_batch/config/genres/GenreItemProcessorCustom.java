package ru.otus.book_storage_batch.config.genres;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.otus.book_storage_batch.dto.genres.GenreInputDto;
import ru.otus.book_storage_batch.dto.genres.GenreOutputDto;
import ru.otus.book_storage_batch.utils.ObjectIdUtils;

@Configuration
public class GenreItemProcessorCustom {


    @Bean
    public ItemProcessor<GenreInputDto, GenreOutputDto> genreProcessor() {
        return new GenreItemProcessor();
    }

    private static class GenreItemProcessor implements ItemProcessor<GenreInputDto, GenreOutputDto> {

        @Override
        public GenreOutputDto process(GenreInputDto item) {
            GenreOutputDto genreOutputDto = new GenreOutputDto();
            genreOutputDto.setId(ObjectIdUtils.getObjectId());
            genreOutputDto.setName(item.getName());
            return genreOutputDto;
        }
    }
}
