package ru.otus.book_storage.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import ru.otus.book_storage.dao.author.AuthorRepository;
import ru.otus.book_storage.dto.AuthorResponseDto;

@RestController
@RequiredArgsConstructor
public class AuthorController {

    private final AuthorRepository authorRepository;

    @GetMapping(value = "/authors")
    public Flux<AuthorResponseDto> getAllAuthors() {
        return authorRepository
                .findAll()
                .map(AuthorResponseDto::new);
    }
}
