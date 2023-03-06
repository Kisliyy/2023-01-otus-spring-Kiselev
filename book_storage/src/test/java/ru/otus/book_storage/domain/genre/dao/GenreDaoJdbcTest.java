package ru.otus.book_storage.domain.genre.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import ru.otus.book_storage.domain.genre.model.Genre;
import ru.otus.book_storage.helper.KeyHolderFactory;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = GenreDaoJdbc.class)
class GenreDaoJdbcTest {

    @MockBean
    private NamedParameterJdbcOperations namedParameterJdbcOperations;
    @MockBean
    private KeyHolderFactory keyHolderFactory;
    @Autowired
    private GenreDao genreDao;

    @Test
    @DisplayName(value = "Get all genres")
    void getAllGenresTest() {
        when(namedParameterJdbcOperations
                .query(
                        anyString(),
                        any(RowMapper.class)
                )
        )
                .thenReturn(List.of(
                        new Genre(),
                        new Genre(),
                        new Genre()
                ));
        List<Genre> allGenres = genreDao.getAll();
        assertEquals(3, allGenres.size());
        verify(namedParameterJdbcOperations, times(1)).query(anyString(), any(RowMapper.class));
    }

    @Test
    @DisplayName("Find by genre successful")
    void getByGenreSuccessfulTest() {
        String bookGenre = "test";
        Long genreId = 1L;
        Genre genre = Genre.builder()
                .id(genreId)
                .genre(bookGenre)
                .build();
        when(namedParameterJdbcOperations
                .queryForObject(
                        anyString(),
                        anyMap(),
                        any(RowMapper.class)

                ))
                .thenReturn(genre);
        Genre findGenre = genreDao.getByGenre(bookGenre);
        assertEquals(genre, findGenre);
        verify(namedParameterJdbcOperations, times(1)).queryForObject(anyString(), anyMap(), any(RowMapper.class));
    }

    @Test
    @DisplayName("Find by genre return null")
    void getByGenreReturnNullTest() {
        String bookGenre = "test";

        when(namedParameterJdbcOperations
                .queryForObject(
                        anyString(),
                        anyMap(),
                        any(RowMapper.class)

                ))
                .thenThrow(EmptyResultDataAccessException.class);

        Genre findGenre = genreDao.getByGenre(bookGenre);
        assertNull(findGenre);
        verify(namedParameterJdbcOperations, times(1)).queryForObject(anyString(), anyMap(), any(RowMapper.class));
    }

    @Test
    @DisplayName("Save genre")
    void saveTest() {
        KeyHolder keyHolder = Mockito.mock(GeneratedKeyHolder.class);
        long genreId = 1;
        int update = 1;
        String bookGenre = "test";
        Genre genre = Genre.builder()
                .genre(bookGenre)
                .build();

        when(keyHolderFactory.newKeyHolder()).thenReturn(keyHolder);
        when(keyHolder.getKey()).thenReturn(genreId);
        when(namedParameterJdbcOperations
                .update(
                        anyString(),
                        any(SqlParameterSource.class),
                        eq(keyHolder),
                        any()
                )
        )
                .thenReturn(update);
        long id = genreDao.save(genre);
        verify(namedParameterJdbcOperations, times(1))
                .update(anyString(), any(SqlParameterSource.class), eq(keyHolder), any());
        verify(keyHolderFactory, times(1)).newKeyHolder();
        assertEquals(genreId, id);
    }
}