package ru.otus.book_storage.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.book_storage.dto.GenreResponseDto;
import ru.otus.book_storage.service.genre.GenreService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class GenreController {

    private final GenreService genreService;

    @GetMapping(value = "/genres")
    public ResponseEntity<List<GenreResponseDto>> getAllGenres() {
        List<GenreResponseDto> response = genreService
                .getAll()
                .stream()
                .map(GenreResponseDto::new)
                .collect(Collectors.toList());
        return ResponseEntity
                .ok(response);
    }
}
