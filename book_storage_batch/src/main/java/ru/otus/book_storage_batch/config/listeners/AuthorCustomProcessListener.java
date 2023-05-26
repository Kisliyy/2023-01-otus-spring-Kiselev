package ru.otus.book_storage_batch.config.listeners;

import org.springframework.stereotype.Component;
import ru.otus.book_storage_batch.dto.authors.AuthorInputDto;
import ru.otus.book_storage_batch.dto.authors.AuthorOutputDto;
import ru.otus.book_storage_batch.exceptions.NotFoundException;

import java.util.HashMap;
import java.util.Map;

@Component
public class AuthorCustomProcessListener implements CustomProcessListener<Long, AuthorInputDto, AuthorOutputDto> {

    private final Map<Long, AuthorOutputDto> authors = new HashMap<>();

    @Override
    public void beforeProcess(AuthorInputDto item) {

    }

    @Override
    public void afterProcess(AuthorInputDto item, AuthorOutputDto result) {
        Long id = item.getId();
        authors.put(id, result);
    }

    @Override
    public void onProcessError(AuthorInputDto item, Exception e) {

    }

    @Override
    public AuthorOutputDto getByRelationId(Long id) {
        AuthorOutputDto authorOutputDto = authors.get(id);
        if (authorOutputDto == null) {
            throw new NotFoundException("Author not found by id: " + id);
        }
        return authorOutputDto;
    }
}
