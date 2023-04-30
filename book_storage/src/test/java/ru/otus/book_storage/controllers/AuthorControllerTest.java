package ru.otus.book_storage.controllers;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import ru.otus.book_storage.dao.author.AuthorRepository;
import ru.otus.book_storage.dto.AuthorResponseDto;
import ru.otus.book_storage.models.Author;

import java.util.List;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

@WebFluxTest(controllers = AuthorController.class)
class AuthorControllerTest {

    @MockBean
    private AuthorRepository authorRepository;

    @Autowired
    private WebTestClient webTestClient;


    @Test
    void shouldReturnFluxAuthorsDto() {
        final String authorId = "1000L";
        final String firstName = "firstName";
        final String lastName = "lastName";
        Author author = new Author(authorId, firstName, lastName);
        AuthorResponseDto authorResponseDto = new AuthorResponseDto(author);
        List<Author> authorList = List.of(author);

        when(authorRepository.findAll()).thenReturn(Flux.fromIterable(authorList));

        webTestClient.get()
                .uri("/authors")
                .exchange()
                .expectStatus()
                .isOk()
                .expectBodyList(AuthorResponseDto.class)
                .hasSize(1)
                .contains(authorResponseDto);

        Mockito.verify(authorRepository, times(1)).findAll();
    }
}