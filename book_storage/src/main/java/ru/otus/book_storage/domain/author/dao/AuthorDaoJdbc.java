package ru.otus.book_storage.domain.author.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.otus.book_storage.domain.author.model.Author;
import ru.otus.book_storage.helper.KeyHolderFactory;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class AuthorDaoJdbc implements AuthorDao {

    private final NamedParameterJdbcOperations namedParameterJdbcOperations;
    private final KeyHolderFactory keyHolderFactory;


    @Override
    public long save(Author author) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("first_name", author.getFirstName());
        params.addValue("last_name", author.getLastName());
        KeyHolder keyHolder = keyHolderFactory.newKeyHolder();
        namedParameterJdbcOperations.update(
                "INSERT INTO authors (first_name, last_name) VALUES (:first_name, :last_name)",
                params,
                keyHolder,
                new String[]{"id"}
        );
        return keyHolder.getKey().longValue();
    }

    @Override
    public List<Author> getAll() {
        return namedParameterJdbcOperations.query(
                "SELECT id, first_name, last_name FROM authors",
                new AuthorMapper()
        );
    }

    @Override
    public Author findByFirstNameAndLastName(String firstName, String lastName) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("first_name", firstName);
        params.addValue("last_name", lastName);
        try {
            return namedParameterJdbcOperations.queryForObject(
                    "SELECT id, first_name, last_name FROM authors WHERE first_name = :first_name and last_name = :last_name",
                    params,
                    new AuthorMapper()
            );
        } catch (EmptyResultDataAccessException exception) {
            return null;
        }
    }

    private static class AuthorMapper implements RowMapper<Author> {

        @Override
        public Author mapRow(ResultSet resultSet, int i) throws SQLException {
            long id = resultSet.getLong("id");
            String firstName = resultSet.getString("first_name");
            String lastName = resultSet.getString("last_name");

            return Author.builder()
                    .id(id)
                    .firstName(firstName)
                    .lastName(lastName)
                    .build();
        }
    }
}
