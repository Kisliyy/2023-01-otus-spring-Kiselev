package ru.otus.book_storage.service.author;

import io.github.resilience4j.circuitbreaker.CallNotPermittedException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.otus.book_storage.dao.author.AuthorRepository;
import ru.otus.book_storage.exceptions.NotFoundException;
import ru.otus.book_storage.models.Author;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;


    @Override
    @Retry(name = "Service_circuitbreaker")
    @CircuitBreaker(name = "Service_circuitbreaker", fallbackMethod = "fallbackGetAll")
    public List<Author> getAll() {
        return authorRepository.findAll();
    }

    @Override
    @CircuitBreaker(name = "Service_circuitbreaker", fallbackMethod = "fallbackFindById")
    public Author findById(Long id) {
        return authorRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException("Author not found by id: " + id));
    }

    private List<Author> fallbackGetAll(CallNotPermittedException ex) {
        log.error(ex.getMessage());
        return Collections.emptyList();
    }

    private Author fallbackFindById(Long id, CallNotPermittedException ex) {
        log.error(ex.getMessage());
        return Author.builder().id(id).firstName("N/A").lastName("N/A").build();
    }
}
