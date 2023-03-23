package ru.otus.book_storage.dao.book;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import ru.otus.book_storage.models.Author;
import ru.otus.book_storage.models.Book;
import ru.otus.book_storage.models.Genre;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Import(BookDaoJpa.class)
@ActiveProfiles("test")
class BookDaoJpaTest {

    @Autowired
    private BookDao bookDao;

    @Autowired
    private TestEntityManager testEntityManager;

    private Author persistAuthor;
    private Genre persistGenre;

    private final String title = "title";

    private final Long existAuthorId = 1L;
    private final Long existGenreId = 1L;

    @BeforeEach
    void init() {
        persistAuthor = testEntityManager.find(Author.class, existAuthorId);
        persistGenre = testEntityManager.find(Genre.class, existGenreId);
    }

    @Test
    void saveNewBookTest() {
        Book newBook = Book.builder()
                .author(persistAuthor)
                .title(title)
                .genre(persistGenre)
                .build();
        Book persistBook = bookDao.save(newBook);
        assertNotNull(persistBook.getId());
        assertEquals(title, persistBook.getTitle());
        assertEquals(persistAuthor, persistBook.getAuthor());
        assertEquals(persistGenre, persistBook.getGenre());
    }

    @Test
    void deleteByIdBookTest() {
        Book newBook = Book.builder()
                .author(persistAuthor)
                .title(title)
                .genre(persistGenre)
                .build();
        Book persistBook = testEntityManager.persist(newBook);
        bookDao.deleteById(persistBook.getId());
        Book book = testEntityManager.find(Book.class, persistBook.getId());
        assertNull(book);
    }

    @Test
    void findByIdBookTest() {
        Long existIdBook = 1L;
        Optional<Book> byId = bookDao.getById(existIdBook);
        assertFalse(byId.isEmpty());
        Book book = byId.get();
        assertEquals(existIdBook, book.getId());
        assertNotNull(book.getGenre());
        assertNotNull(book.getAuthor());
    }

    @Test
    void getAllExistBooksTest() {
        List<Book> allBooks = bookDao.getAll();
        assertNotNull(allBooks);
        assertFalse(allBooks.isEmpty());
        assertTrue(allBooks.stream().allMatch(Objects::nonNull));
        assertEquals(3, allBooks.size());
    }
}