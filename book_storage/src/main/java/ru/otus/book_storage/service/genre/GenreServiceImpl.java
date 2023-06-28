package ru.otus.book_storage.service.genre;

import io.github.resilience4j.circuitbreaker.CallNotPermittedException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.otus.book_storage.dao.genre.GenreRepository;
import ru.otus.book_storage.exceptions.NotFoundException;
import ru.otus.book_storage.models.Genre;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class GenreServiceImpl implements GenreService {

    private final GenreRepository genreRepository;

    @Override
    @CircuitBreaker(name = "Service_circuitbreaker", fallbackMethod = "fallbackGetAll")
    @Retry(name = "Service_circuitbreaker")
    public List<Genre> getAll() {
        return genreRepository.findAll();
    }

    @Override
    @CircuitBreaker(name = "Service_circuitbreaker", fallbackMethod = "fallbackGetById")
    public Genre getById(Long id) {
        return genreRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException("Genre not found by id: " + id));
    }

    public List<Genre> fallbackGetAll(CallNotPermittedException exception) {
        log.error(exception.getMessage());
        return Collections.emptyList();
    }

    public Genre fallbackGetById(Long id, CallNotPermittedException exception) {
        log.error(exception.getMessage());
        return Genre.builder()
                .id(id)
                .name("N/A")
                .build();
    }
}
