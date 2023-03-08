package ru.otus.book_storage.domain.author.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import ru.otus.book_storage.domain.author.model.Author;
import ru.otus.book_storage.helper.KeyHolderFactory;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = AuthorDaoJdbc.class)
class AuthorDaoJdbcTest {

    @MockBean
    private NamedParameterJdbcOperations namedParameterJdbcOperations;
    @MockBean
    private KeyHolderFactory keyHolderFactory;
    @Autowired
    private AuthorDao authorDao;

    @Test
    @DisplayName("Save author")
    void saveTest() {
        KeyHolder keyHolder = Mockito.mock(GeneratedKeyHolder.class);
        String firstName = "firstName";
        String lastName = "lastName";
        long authorId = 1;
        int update = 1;

        Author author = Author.builder()
                .firstName(firstName)
                .lastName(lastName)
                .build();
        when(keyHolderFactory.newKeyHolder()).thenReturn(keyHolder);
        when(keyHolder.getKey()).thenReturn(authorId);
        when(
                namedParameterJdbcOperations
                        .update(
                                anyString(),
                                any(SqlParameterSource.class),
                                eq(keyHolder),
                                any()
                        )
        )
                .thenReturn(update);

        long id = authorDao.save(author);
        verify(namedParameterJdbcOperations, times(1))
                .update(anyString(), any(SqlParameterSource.class), eq(keyHolder), any());
        verify(keyHolderFactory, times(1)).newKeyHolder();
        assertEquals(authorId, id);
    }

    @Test
    @DisplayName("Get all authors")
    void getAllTest() {
        when(
                namedParameterJdbcOperations
                        .query(
                                anyString(),
                                any(RowMapper.class)
                        )
        ).thenReturn(
                List.of(
                        new Author(),
                        new Author(),
                        new Author()
                )
        );

        List<Author> allAuthors = authorDao.getAll();
        assertNotNull(allAuthors);
        assertEquals(3, allAuthors.size());
    }

    @Test
    @DisplayName("Find author by first name and last name")
    void findByFirstNameAndLastName() {
        String firstName = "firstName";
        String lastName = "lastName";
        long authorId = 1L;
        Author existAuthor = Author.builder()
                .id(authorId)
                .firstName(firstName)
                .lastName(lastName)
                .build();

        when(
                namedParameterJdbcOperations
                        .queryForObject(
                                anyString(),
                                any(MapSqlParameterSource.class),
                                any(RowMapper.class)
                        )
        )
                .thenReturn(existAuthor);

        Author findAuthor = authorDao.findByFirstNameAndLastName(firstName, lastName);
        assertNotNull(findAuthor);
        assertEquals(firstName, findAuthor.getFirstName());
        assertEquals(lastName, findAuthor.getLastName());
        assertEquals(existAuthor, findAuthor);
    }
}