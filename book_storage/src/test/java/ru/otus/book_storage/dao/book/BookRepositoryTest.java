package ru.otus.book_storage.dao.book;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ActiveProfiles;
import ru.otus.book_storage.models.Book;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataMongoTest
@ComponentScan({"ru.otus.book_storage.dao", "ru.otus.book_storage.config"})
@ActiveProfiles("test")
class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;

    @Test
    void shouldReturnCorrectBooksList() {
        List<Book> allBooks = bookRepository.findAll();
        assertNotNull(allBooks);
        assertEquals(3, allBooks.size());
    }
}