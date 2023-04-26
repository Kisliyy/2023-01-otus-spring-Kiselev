package ru.otus.book_storage.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.otus.book_storage.dto.BookResponseDto;
import ru.otus.book_storage.dto.CreateBookDto;
import ru.otus.book_storage.dto.UpdateBookDto;
import ru.otus.book_storage.models.Book;
import ru.otus.book_storage.service.book.BookService;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @GetMapping(value = "/books")
    public ResponseEntity<List<BookResponseDto>> allBook() {
        List<BookResponseDto> response = bookService
                .getAllBook()
                .stream()
                .map(BookResponseDto::new)
                .collect(Collectors.toList());
        return ResponseEntity
                .ok(response);
    }

    @GetMapping(value = "/books/{id}")
    public ResponseEntity<BookResponseDto> bookById(@PathVariable(name = "id") Long id) {
        Book bookById = bookService.getById(id);
        return ResponseEntity
                .ok(new BookResponseDto(bookById));
    }

    @PostMapping(value = "/books", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BookResponseDto> addBook(@Valid @RequestBody CreateBookDto createBookDto) {
        Book persistentBook = bookService.save(createBookDto.toDomainObject());
        return ResponseEntity
                .ok(new BookResponseDto(persistentBook));
    }

    @DeleteMapping(value = "/books")
    public ResponseEntity<?> deleteBook(@RequestParam(name = "id") Long id) {
        bookService.deleteById(id);
        return ResponseEntity
                .ok()
                .build();
    }

    @PutMapping(value = "/books", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateBook(@Valid @RequestBody UpdateBookDto updateBookDto) {
        bookService.updateBook(updateBookDto);
        return ResponseEntity
                .ok()
                .build();
    }
}
