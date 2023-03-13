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

    private final String title = "title";

    private Author newAuthor;
    private Genre newGenre;

    @BeforeEach
    void init() {
        String firstName = "FirstName";
        String lastName = "LastName";
        newAuthor = Author.builder()
                .firstName(firstName)
                .lastName(lastName)
                .build();
        String bookGenre = "genre";
        newGenre = Genre.builder()
                .genre(bookGenre)
                .build();
    }

    @Test
    void saveNewBookTest() {
        Author persistAuthor = testEntityManager.persist(newAuthor);
        Genre persistGenre = testEntityManager.persist(newGenre);

        Book newBook = Book.builder()
                .author(persistAuthor)
                .title(title)
                .genre(persistGenre)
                .build();
        bookDao.save(newBook);
        Book persistBook = testEntityManager.persist(newBook);
        assertNotNull(persistBook.getId());
        assertEquals(persistAuthor, persistBook.getAuthor());
        assertEquals(persistGenre, persistBook.getGenre());
    }

    @Test
    void deleteByIdBookTest() {
        Author persistAuthor = testEntityManager.persist(newAuthor);
        Genre persistGenre = testEntityManager.persist(newGenre);

        Book newBook = Book.builder()
                .author(persistAuthor)
                .title(title)
                .genre(persistGenre)
                .build();
        Book persistBook = testEntityManager.persist(newBook);
        bookDao.deleteById(persistBook.getId());
        testEntityManager.detach(persistBook);
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

    @Test
    void updateBookTest() {
        String newTitle = "newTitle";
        Author persistAuthor = testEntityManager.persist(newAuthor);
        Genre persistGenre = testEntityManager.persist(newGenre);

        Book newBook = Book.builder()
                .author(persistAuthor)
                .title(title)
                .genre(persistGenre)
                .build();
        Book persistBook = testEntityManager.persist(newBook);
        persistBook.setTitle(newTitle);
        bookDao.update(persistBook);
        Book mergeBook = testEntityManager.merge(persistBook);
        assertEquals(persistBook.getId(), mergeBook.getId());
        assertEquals(newTitle, mergeBook.getTitle());
        assertEquals(persistGenre, mergeBook.getGenre());
        assertEquals(persistAuthor, mergeBook.getAuthor());
    }
}