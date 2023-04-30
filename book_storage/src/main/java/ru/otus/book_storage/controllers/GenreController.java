package ru.otus.book_storage.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import ru.otus.book_storage.dao.genre.GenreRepository;
import ru.otus.book_storage.dto.GenreResponseDto;

@RestController
@RequiredArgsConstructor
public class GenreController {

    private final GenreRepository genreRepository;

    @GetMapping(value = "/genres")
    public Flux<GenreResponseDto> getAllGenres() {
        return genreRepository
                .findAll()
                .map(GenreResponseDto::new);
    }
}
