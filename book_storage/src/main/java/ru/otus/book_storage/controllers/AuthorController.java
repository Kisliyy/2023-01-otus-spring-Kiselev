package ru.otus.book_storage.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.book_storage.dto.AuthorResponseDto;
import ru.otus.book_storage.service.author.AuthorService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class AuthorController {

    private final AuthorService authorService;

    @GetMapping(value = "/authors")
    public ResponseEntity<List<AuthorResponseDto>> getAllAuthors() {
        List<AuthorResponseDto> response = authorService
                .getAll()
                .stream()
                .map(AuthorResponseDto::new)
                .collect(Collectors.toList());
        return ResponseEntity
                .ok(response);
    }
}
