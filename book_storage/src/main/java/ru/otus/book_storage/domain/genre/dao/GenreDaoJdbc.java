package ru.otus.book_storage.domain.genre.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.otus.book_storage.domain.genre.model.Genre;
import ru.otus.book_storage.helper.KeyHolderFactory;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Repository
public class GenreDaoJdbc implements GenreDao {

    private final NamedParameterJdbcOperations namedParameterJdbcOperations;
    private final KeyHolderFactory keyHolderFactory;


    @Override
    public List<Genre> getAll() {
        return namedParameterJdbcOperations.query(
                "SELECT id, genre FROM genres",
                new GenreMapper()
        );
    }

    @Override
    public Genre getByGenre(String genre) {
        try {
            return namedParameterJdbcOperations.queryForObject(
                    "SELECT id, genre FROM genres WHERE genre = :genre",
                    Map.of("genre", genre),
                    new GenreMapper()
            );
        } catch (EmptyResultDataAccessException exception) {
            return null;
        }
    }

    @Override
    public long save(Genre genre) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("genre", genre.getGenre());
        KeyHolder keyHolder = keyHolderFactory.newKeyHolder();
        namedParameterJdbcOperations.update(
                "INSERT INTO genres (genre) VALUES (:genre)",
                params,
                keyHolder,
                new String[]{"id"}
        );
        return keyHolder
                .getKey()
                .longValue();
    }

    private static class GenreMapper implements RowMapper<Genre> {

        @Override
        public Genre mapRow(ResultSet resultSet, int i) throws SQLException {
            long genreId = resultSet.getLong("id");
            String genreBook = resultSet.getString("genre");
            return Genre.builder()
                    .id(genreId)
                    .genre(genreBook)
                    .build();
        }
    }
}
