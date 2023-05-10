package ru.otus.book_storage.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.book_storage.dao.author.AuthorRepository;
import ru.otus.book_storage.dao.book.BookRepository;
import ru.otus.book_storage.dao.comment.CommentRepository;
import ru.otus.book_storage.dao.genre.GenreRepository;
import ru.otus.book_storage.dto.BookResponseDto;
import ru.otus.book_storage.dto.CreateBookDto;
import ru.otus.book_storage.dto.UpdateBookDto;
import ru.otus.book_storage.exceptions.NotFoundException;
import ru.otus.book_storage.models.Author;
import ru.otus.book_storage.models.Book;
import ru.otus.book_storage.models.Genre;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class BookController {


    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final GenreRepository genreRepository;

    private final CommentRepository commentRepository;

    @GetMapping(value = "/books")
    public Flux<BookResponseDto> allBook() {
        return bookRepository
                .findAll()
                .map(BookResponseDto::new);
    }

    @GetMapping(value = "/books/{id}")
    public Mono<BookResponseDto> bookById(@PathVariable(name = "id") String id) {
        return findBook(id)
                .map(BookResponseDto::new);
    }

    @PostMapping(value = "/books")
    public Mono<BookResponseDto> addBook(@Valid @RequestBody CreateBookDto createBookDto) {
        Mono<Author> findAuthor = findAuthor(createBookDto.getAuthorId());
        Mono<Genre> findGenre = findGenre(createBookDto.getGenreId());

        Mono<Book> saveBook = Mono.zip(findAuthor, findGenre).map(data -> {
            Author author = data.getT1();
            Genre genre = data.getT2();
            return Book.builder()
                    .title(createBookDto.getTitle())
                    .genre(genre)
                    .author(author)
                    .build();
        });
        return saveBook
                .flatMap(bookRepository::save)
                .map(BookResponseDto::new);
    }

    @DeleteMapping(value = "/books")
    public Mono<Void> deleteBook(@RequestParam(name = "id") String id) {
        Mono<Book> bookMono = findBook(id);

        bookMono
                .mapNotNull(Book::getComments)
                .subscribe(commentRepository::deleteAll);

        return bookMono
                .flatMap(bookRepository::delete);
    }

    @PutMapping(value = "/books", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<BookResponseDto> updateBook(@Valid @RequestBody UpdateBookDto updateBookDto) {
        Mono<Author> findAuthor = findAuthor(updateBookDto.getAuthorId());
        Mono<Genre> findGenre = findGenre(updateBookDto.getGenreId());
        Mono<Book> findBook = findBook(updateBookDto.getId());

        Mono<Book> updateBook = Mono.zip(findAuthor, findGenre, findBook).map(data -> {
            Author author = data.getT1();
            Genre genre = data.getT2();
            Book book = data.getT3();
            book.setAuthor(author);
            book.setGenre(genre);
            book.setTitle(updateBookDto.getTitle());
            return book;
        });

        return updateBook
                .flatMap(bookRepository::save)
                .map(BookResponseDto::new);
    }

    private Mono<Author> findAuthor(String authorId) {
        return authorRepository
                .findById(authorId)
                .switchIfEmpty(Mono.error(() -> new NotFoundException("Author not found by id: " + authorId)));
    }

    private Mono<Genre> findGenre(String genreId) {
        return genreRepository
                .findById(genreId)
                .switchIfEmpty(Mono.error(() -> new NotFoundException("Genre not found by id: " + genreId)));
    }

    private Mono<Book> findBook(String bookId) {
        return bookRepository
                .findById(bookId)
                .switchIfEmpty(Mono.error(() -> new NotFoundException("Book not found by id: " + bookId)));
    }
}
