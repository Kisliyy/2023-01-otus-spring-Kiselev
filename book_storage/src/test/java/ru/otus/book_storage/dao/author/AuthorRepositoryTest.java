package ru.otus.book_storage.dao.author;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ActiveProfiles;
import ru.otus.book_storage.models.Author;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


@DataMongoTest
@ComponentScan({"ru.otus.book_storage.dao", "ru.otus.book_storage.config"})
@ActiveProfiles("test")
class AuthorRepositoryTest {

    @Autowired
    private AuthorRepository authorRepository;

    @Test
    void shouldReturnCorrectAuthorsList() {
        List<Author> allAuthors = authorRepository.findAll();
        assertNotNull(allAuthors);
        assertEquals(3, allAuthors.size());
    }
}