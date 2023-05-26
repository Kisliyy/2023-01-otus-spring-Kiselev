package ru.otus.book_storage_batch.config.authors;

import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.database.builder.JdbcCursorItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.RowMapper;
import ru.otus.book_storage_batch.dto.authors.AuthorInputDto;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;

@Configuration
public class AuthorReader {

    private final static String SQL_QUERY = "SELECT * FROM authors";


    @Bean
    public JdbcCursorItemReader<AuthorInputDto> authorItemReader(DataSource dataSource) {
        return new JdbcCursorItemReaderBuilder<AuthorInputDto>()
                .name("authorItemReader")
                .dataSource(dataSource)
                .sql(SQL_QUERY)
                .rowMapper(new AuthorInputRowMapper())
                .build();
    }

    private static class AuthorInputRowMapper implements RowMapper<AuthorInputDto> {

        @Override
        public AuthorInputDto mapRow(ResultSet rs, int rowNum) throws SQLException {
            long id = rs.getLong("id");
            String firstName = rs.getString("first_name");
            String lastName = rs.getString("last_name");

            return new AuthorInputDto(id, firstName, lastName);
        }
    }
}
