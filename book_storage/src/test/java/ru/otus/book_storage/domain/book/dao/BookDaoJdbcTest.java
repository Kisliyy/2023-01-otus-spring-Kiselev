package ru.otus.book_storage.domain.book.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import ru.otus.book_storage.domain.author.model.Author;
import ru.otus.book_storage.domain.book.model.Book;
import ru.otus.book_storage.domain.genre.model.Genre;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = BookDaoJdbc.class)
class BookDaoJdbcTest {

    @MockBean
    private NamedParameterJdbcOperations namedParameterJdbcOperations;
    @Autowired
    private BookDao bookDao;

    @Test
    @DisplayName("Save book successful")
    void saveTestSuccessful() {
        int update = 1;
        long authorId = 1L;
        long genreId = 1L;
        String bookGenre = "genre";
        String title = "test";
        String firstName = "firstName";
        String lastName = "lastName";
        Author existAuthor = Author.builder()
                .id(authorId)
                .firstName(firstName)
                .lastName(lastName)
                .build();
        Genre existGenre = Genre.builder()
                .genre(bookGenre)
                .id(genreId)
                .build();
        Book savedBook = Book.builder()
                .genre(existGenre)
                .author(existAuthor)
                .title(title)
                .build();


        when(namedParameterJdbcOperations.update(anyString(), any(MapSqlParameterSource.class))).thenReturn(update);
        bookDao.save(savedBook);
        verify(namedParameterJdbcOperations, times(1)).update(anyString(), any(MapSqlParameterSource.class));
    }

    @Test
    @DisplayName("Delete book by id")
    void deleteBookByIdTest() {
        long bookId = 1L;
        int updateReturn = 1;
        when(namedParameterJdbcOperations.
                update(
                        anyString(),
                        anyMap()
                )
        )
                .thenReturn(updateReturn);
        bookDao.deleteById(bookId);
        verify(namedParameterJdbcOperations, times(1)).update(anyString(), anyMap());
    }

    @Test
    @DisplayName("Get book by id successful")
    void getByIdSuccessfulTest() {
        String title = "test";
        long bookId = 1L;
        Book existBook = Book.builder()
                .id(bookId)
                .title(title)
                .build();

        when(
                namedParameterJdbcOperations
                        .queryForObject(
                                anyString(),
                                anyMap(),
                                any(RowMapper.class)
                        )
        )
                .thenReturn(existBook);
        Book findBook = bookDao.getById(bookId);
        assertEquals(existBook, findBook);
        assertEquals(bookId, findBook.getId());
        verify(namedParameterJdbcOperations, times(1)).queryForObject(anyString(), anyMap(), any(RowMapper.class));
    }

    @Test
    @DisplayName("Get book by id return null")
    void getByIdReturnNullTest() {
        long bookId = 1L;
        when(
                namedParameterJdbcOperations
                        .queryForObject(
                                anyString(),
                                anyMap(),
                                any(RowMapper.class)
                        )
        )
                .thenThrow(EmptyResultDataAccessException.class);
        Book findBook = bookDao.getById(bookId);
        assertNull(findBook);
        verify(namedParameterJdbcOperations, times(1)).queryForObject(anyString(), anyMap(), any(RowMapper.class));

    }

    @Test
    @DisplayName("Get all books")
    void getAllTest() {
        when(
                namedParameterJdbcOperations
                        .query(
                                anyString(),
                                any(RowMapper.class)
                        )
        )
                .thenReturn(
                        List.of(
                                new Book(),
                                new Book(),
                                new Book()
                        )
                );

        List<Book> allBooks = bookDao.getAll();
        assertNotNull(allBooks);
        assertEquals(3, allBooks.size());
        verify(namedParameterJdbcOperations, times(1)).query(anyString(), any(RowMapper.class));
    }

    @Test
    @DisplayName("Update book")
    void updateTest() {
        int update = 1;
        long authorId = 1L;
        long genreId = 1L;
        String bookGenre = "genre";
        String title = "test";
        String firstName = "firstName";
        String lastName = "lastName";

        Author updateAuthor = Author.builder()
                .id(authorId)
                .firstName(firstName)
                .lastName(lastName)
                .build();

        Genre updateGenre = Genre.builder()
                .genre(bookGenre)
                .id(genreId)
                .build();

        Book updateBook = Book.builder()
                .genre(updateGenre)
                .author(updateAuthor)
                .title(title)
                .build();


        when(namedParameterJdbcOperations.update(anyString(), any(MapSqlParameterSource.class))).thenReturn(update);
        bookDao.update(updateBook);
        verify(namedParameterJdbcOperations, times(1)).update(anyString(), any(MapSqlParameterSource.class));
    }
}