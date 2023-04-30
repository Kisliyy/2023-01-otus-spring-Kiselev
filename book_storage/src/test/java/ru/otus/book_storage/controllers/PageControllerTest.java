package ru.otus.book_storage.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@WebFluxTest(controllers = PageController.class)
class PageControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    void allBookVerifyTitlePageTest() {
        webTestClient.get()
                .uri("/")
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.TEXT_HTML)
                .expectBody().xpath("//head/title/text()", "List of all books").exists();
    }


    @Test
    void createBookVerifyTitlePageTest() {
        webTestClient.get()
                .uri("/add")
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.TEXT_HTML)
                .expectBody().xpath("//head/title/text()", "Create book").exists();
    }

    @Test
    void editBookVerifyTitlePageTest() {
        String bookId = "123";
        URI uri = UriComponentsBuilder
                .fromPath("/edit")
                .queryParam("id", bookId)
                .build()
                .toUri();

        webTestClient.get()
                .uri(uri)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.TEXT_HTML)
                .expectBody().xpath("//head/title/text()", "Edit book").exists();
    }
}