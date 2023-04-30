package ru.otus.book_storage.controllers;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import ru.otus.book_storage.dao.genre.GenreRepository;
import ru.otus.book_storage.dto.GenreResponseDto;
import ru.otus.book_storage.models.Genre;

import java.util.List;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

@WebFluxTest(controllers = GenreController.class)
class GenreControllerTest {

    @MockBean
    private GenreRepository genreRepository;

    @Autowired
    private WebTestClient webTestClient;


    @Test
    void shouldReturnFluxGenresDto() {
        final String genreId = "1000L";
        final String genreName = "genreName";
        Genre genre = new Genre(genreId, genreName);
        GenreResponseDto genreResponseDto = new GenreResponseDto(genre);
        List<Genre> genreList = List.of(genre);


        when(genreRepository.findAll()).thenReturn(Flux.fromIterable(genreList));

        webTestClient.get()
                .uri("/genres")
                .exchange()
                .expectStatus()
                .isOk()
                .expectBodyList(GenreResponseDto.class)
                .hasSize(1)
                .contains(genreResponseDto);

        Mockito.verify(genreRepository, times(1)).findAll();
    }
}