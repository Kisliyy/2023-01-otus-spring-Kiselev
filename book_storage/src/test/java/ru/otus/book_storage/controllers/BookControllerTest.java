package ru.otus.book_storage.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.book_storage.dao.author.AuthorRepository;
import ru.otus.book_storage.dao.book.BookRepository;
import ru.otus.book_storage.dao.comment.CommentRepository;
import ru.otus.book_storage.dao.genre.GenreRepository;
import ru.otus.book_storage.dto.BookResponseDto;
import ru.otus.book_storage.dto.CreateBookDto;
import ru.otus.book_storage.dto.UpdateBookDto;
import ru.otus.book_storage.models.Author;
import ru.otus.book_storage.models.Book;
import ru.otus.book_storage.models.Genre;

import java.net.URI;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.mockito.internal.verification.VerificationModeFactory.times;

@WebFluxTest(controllers = BookController.class)
class BookControllerTest {


    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private BookRepository bookRepository;

    @MockBean
    private AuthorRepository authorRepository;

    @MockBean
    private GenreRepository genreRepository;

    @MockBean
    private CommentRepository commentRepository;

    private Book book;

    private Author author;

    private Genre genre;

    final String authorId = "1000";
    final String genreId = "1000";
    final String bookId = "1000";

    private final String title = "title";
    private final String genreName = "genreName";
    private final String firstName = "firstName";
    private final String lastName = "lastName";


    @BeforeEach
    void init() {
        this.author = new Author(authorId, firstName, lastName);
        this.genre = new Genre(genreId, genreName);
        this.book = Book.builder()
                .id(bookId)
                .title(title)
                .author(author)
                .genre(genre)
                .comments(Collections.emptySet())
                .build();
    }


    @Test
    void shouldReturnCorrectListBookDto() {
        BookResponseDto bookResponseDto = new BookResponseDto(book);
        List<Book> bookList = List.of(book);
        when(bookRepository.findAll()).thenReturn(Flux.fromIterable(bookList));

        webTestClient
                .get()
                .uri("/books")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(BookResponseDto.class)
                .hasSize(1)
                .contains(bookResponseDto);

        verify(bookRepository, times(1)).findAll();
    }

    @Test
    void shouldReturnFindBookDto() {
        when(bookRepository.findById(bookId)).thenReturn(Mono.just(book));
        final String url = "/books/" + bookId;
        webTestClient.get()
                .uri(url)
                .exchange()
                .expectStatus().isOk()
                .expectBody().jsonPath("$.title", title).hasJsonPath()
                .jsonPath("$.genreName", genreName).hasJsonPath()
                .jsonPath("$.firstNameAuthor", firstName).hasJsonPath()
                .jsonPath("$.lastNameAuthor", lastName).hasJsonPath()
                .jsonPath("$.countComment", 0).hasJsonPath();

        verify(bookRepository, times(1)).findById(bookId);
    }

    @Test
    void shouldReturnNotFoundByFoundBookException() {
        String message = "Book not found by id: " + bookId;
        when(bookRepository.findById(anyString())).thenReturn(Mono.empty());

        final String url = "/books/" + bookId;

        webTestClient.get()
                .uri(url)
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody().jsonPath("$.message", message).hasJsonPath();

        verify(bookRepository, times(1)).findById(anyString());
    }

    @Test
    void shouldAddNewBookTest() {
        var saveBookId = "saveBookId";

        CreateBookDto createBookDto = new CreateBookDto(title, authorId, genreId);


        Book saveBook = Book.builder()
                .author(author)
                .genre(genre)
                .title(title)
                .build();

        Book persistBook = Book.builder()
                .id(saveBookId)
                .author(author)
                .genre(genre)
                .title(title)
                .build();

        when(authorRepository.findById(authorId)).thenReturn(Mono.just(author));
        when(genreRepository.findById(genreId)).thenReturn(Mono.just(genre));
        when(bookRepository.save(saveBook)).thenReturn(Mono.just(persistBook));

        webTestClient
                .post()
                .uri("/books")
                .bodyValue(createBookDto)
                .exchange()
                .expectStatus().isOk()
                .expectBody().jsonPath("$.title", title).hasJsonPath()
                .jsonPath("$.genreName", genreName).hasJsonPath()
                .jsonPath("$.firstNameAuthor", firstName).hasJsonPath()
                .jsonPath("$.lastNameAuthor", lastName).hasJsonPath()
                .jsonPath("$.countComment", 0).hasJsonPath();

        verify(authorRepository, times(1)).findById(authorId);
        verify(genreRepository, times(1)).findById(genreId);
        verify(bookRepository, times(1)).save(saveBook);
    }

    @Test
    void shouldReturnExceptionByAddNewBookTest() {
        CreateBookDto createBookDto = new CreateBookDto(null, authorId, genreId);

        webTestClient.post()
                .uri("/books")
                .bodyValue(createBookDto)
                .exchange()
                .expectStatus().isBadRequest();
    }

    @Test
    void shouldDeleteBookTest() {

        when(bookRepository.findById(bookId)).thenReturn(Mono.just(book));
        when(commentRepository.deleteAll(book.getComments())).thenReturn(Mono.empty());
        when(bookRepository.delete(book)).thenReturn(Mono.empty());

        URI uri = UriComponentsBuilder
                .fromPath("/books")
                .queryParam("id", bookId)
                .build()
                .toUri();

        webTestClient.delete()
                .uri(uri)
                .exchange()
                .expectStatus().isOk();

        verify(bookRepository, times(1)).findById(authorId);
        verify(commentRepository, times(1)).deleteAll(book.getComments());
        verify(bookRepository, times(1)).delete(book);
    }

    @Test
    void shouldUpdateBookTest() {
        String newTitle = "newTitle";
        UpdateBookDto updateBookDto = new UpdateBookDto(bookId, newTitle, authorId, genreId);

        Book updateBook = Book.builder()
                .id(bookId)
                .title(newTitle)
                .author(author)
                .genre(genre)
                .comments(Collections.emptySet())
                .build();

        when(authorRepository.findById(authorId)).thenReturn(Mono.just(author));
        when(genreRepository.findById(genreId)).thenReturn(Mono.just(genre));
        when(bookRepository.findById(bookId)).thenReturn(Mono.just(book));
        when(bookRepository.save(updateBook)).thenReturn(Mono.just(updateBook));

        webTestClient.put()
                .uri("/books")
                .bodyValue(updateBookDto)
                .exchange()
                .expectStatus().isOk()
                .expectBody().jsonPath("$.title", newTitle).hasJsonPath()
                .jsonPath("$.genreName", genreName).hasJsonPath()
                .jsonPath("$.firstNameAuthor", firstName).hasJsonPath()
                .jsonPath("$.lastNameAuthor", lastName).hasJsonPath()
                .jsonPath("$.countComment", 0).hasJsonPath();

        verify(bookRepository, times(1)).findById(bookId);
        verify(authorRepository, times(1)).findById(authorId);
        verify(genreRepository, times(1)).findById(genreId);
        verify(bookRepository, times(1)).save(updateBook);
    }

    @Test
    void shouldReturnExceptionByUpdateBookTest() {
        UpdateBookDto updateBookDto = new UpdateBookDto(null, null, authorId, genreId);

        webTestClient.put()
                .uri("/books")
                .bodyValue(updateBookDto)
                .exchange()
                .expectStatus().isBadRequest();
    }
}