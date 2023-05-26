package ru.otus.book_storage_batch.config.listeners;

import org.springframework.stereotype.Component;
import ru.otus.book_storage_batch.dto.genres.GenreInputDto;
import ru.otus.book_storage_batch.dto.genres.GenreOutputDto;
import ru.otus.book_storage_batch.exceptions.NotFoundException;

import java.util.HashMap;
import java.util.Map;

@Component
public class GenreCustomProcessListener implements CustomProcessListener<Long, GenreInputDto, GenreOutputDto> {

    private final Map<Long, GenreOutputDto> genres = new HashMap<>();


    @Override
    public GenreOutputDto getByRelationId(Long id) {
        GenreOutputDto genreOutputDto = genres.get(id);
        if (genreOutputDto == null) {
            throw new NotFoundException("Genre not found by id: " + id);
        }
        return genreOutputDto;
    }

    @Override
    public void beforeProcess(GenreInputDto item) {

    }

    @Override
    public void afterProcess(GenreInputDto item, GenreOutputDto result) {
        Long id = item.getId();
        genres.put(id, result);
    }

    @Override
    public void onProcessError(GenreInputDto item, Exception e) {

    }
}
