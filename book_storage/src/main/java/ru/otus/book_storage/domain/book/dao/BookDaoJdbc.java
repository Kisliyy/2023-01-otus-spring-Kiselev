package ru.otus.book_storage.domain.book.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import ru.otus.book_storage.domain.author.model.Author;
import ru.otus.book_storage.domain.book.model.Book;
import ru.otus.book_storage.domain.genre.model.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class BookDaoJdbc implements BookDao {

    private final NamedParameterJdbcOperations namedParameterJdbcOperations;

    @Override
    public void save(Book book) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("title", book.getTitle());
        params.addValue("author_id", book.getAuthor().getId());
        params.addValue("genre_id", book.getGenre().getId());
        namedParameterJdbcOperations.update(
                "INSERT INTO books (title, author_id, genre_id) VALUES (:title, :author_id, :genre_id)",
                params
        );
    }

    @Override
    public void deleteById(long id) {
        Map<String, Long> params = Collections.singletonMap("id", id);
        namedParameterJdbcOperations.update(
                "DELETE FROM books WHERE id = :id",
                params)
        ;
    }

    @Override
    public Book getById(long id) {
        try {
            Map<String, Long> params = Collections.singletonMap("id", id);
            return namedParameterJdbcOperations.queryForObject(
                    "SELECT b.id, b.title, a.id as author_id, a.first_name, a.last_name, g.id as genre_id, g.genre " +
                            "FROM books as b " +
                            "JOIN authors as a ON b.author_id = a.id " +
                            "JOIN genres as g ON b.genre_id = g.id " +
                            "WHERE b.id = :id",
                    params,
                    new BookMapper()
            );
        } catch (EmptyResultDataAccessException exception) {
            return null;
        }
    }

    @Override
    public List<Book> getAll() {
        return namedParameterJdbcOperations.query(
                "SELECT b.id, b.title, a.id as author_id, a.first_name, a.last_name, g.id as genre_id, g.genre " +
                        "FROM books as b " +
                        "JOIN authors as a ON b.author_id = a.id " +
                        "JOIN genres as g ON b.genre_id = g.id",
                new BookMapper());
    }

    @Override
    public void update(Book book) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", book.getId());
        params.addValue("title", book.getTitle());
        params.addValue("author_id", book.getAuthor().getId());
        params.addValue("genre_id", book.getGenre().getId());
        namedParameterJdbcOperations.update(
                "UPDATE books SET title = :title, author_id = :author_id, genre_id = :genre_id WHERE id = :id",
                params);
    }


    private static class BookMapper implements RowMapper<Book> {

        @Override
        public Book mapRow(ResultSet resultSet, int i) throws SQLException {
            long id = resultSet.getLong("id");
            String title = resultSet.getString("title");

            long authorId = resultSet.getLong("author_id");
            String firstName = resultSet.getString("first_name");
            String lastName = resultSet.getString("last_name");
            Author author = Author.builder()
                    .id(authorId)
                    .firstName(firstName)
                    .lastName(lastName)
                    .build();

            long genreId = resultSet.getLong("genre_id");
            String genreBook = resultSet.getString("genre");
            Genre genre = Genre.builder()
                    .id(genreId)
                    .genre(genreBook)
                    .build();

            return Book.builder()
                    .author(author)
                    .genre(genre)
                    .title(title)
                    .id(id)
                    .build();
        }
    }
}
