package ru.otus.book_storage_batch.config.comments;

import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.database.builder.JdbcCursorItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.RowMapper;
import ru.otus.book_storage_batch.dto.comments.CommentInputDto;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;

@Configuration
public class CommentReader {

    private final static String SQL_QUERY = "SELECT * FROM comments";


    @Bean
    public JdbcCursorItemReader<CommentInputDto> commentItemReader(DataSource dataSource) {
        return new JdbcCursorItemReaderBuilder<CommentInputDto>()
                .name("commentItemReader")
                .dataSource(dataSource)
                .sql(SQL_QUERY)
                .rowMapper(new CommentInputRowMapper())
                .build();
    }

    private static class CommentInputRowMapper implements RowMapper<CommentInputDto> {

        @Override
        public CommentInputDto mapRow(ResultSet rs, int rowNum) throws SQLException {
            long id = rs.getLong("id");
            String text = rs.getString("text");
            long bookId = rs.getLong("book_id");
            return new CommentInputDto(id, text, bookId);
        }
    }
}
