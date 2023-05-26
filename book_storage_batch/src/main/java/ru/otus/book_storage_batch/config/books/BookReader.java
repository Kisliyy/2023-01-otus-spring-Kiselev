package ru.otus.book_storage_batch.config.books;

import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.database.builder.JdbcCursorItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.RowMapper;
import ru.otus.book_storage_batch.dto.books.BookInputDto;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;

@Configuration
public class BookReader {

    private final static String SQL_QUERY = "SELECT * FROM books";


    @Bean
    public JdbcCursorItemReader<BookInputDto> booksItemReader(DataSource dataSource) {
        return new JdbcCursorItemReaderBuilder<BookInputDto>()
                .name("booksItemReader")
                .dataSource(dataSource)
                .sql(SQL_QUERY)
                .rowMapper(new BooksInputRowMapper())
                .build();
    }

    private static class BooksInputRowMapper implements RowMapper<BookInputDto> {

        @Override
        public BookInputDto mapRow(ResultSet rs, int rowNum) throws SQLException {
            long id = rs.getLong("id");
            String title = rs.getString("title");
            long genreId = rs.getLong("genre_id");
            long authorId = rs.getLong("author_id");
            return new BookInputDto(id, title, genreId, authorId);
        }
    }
}
