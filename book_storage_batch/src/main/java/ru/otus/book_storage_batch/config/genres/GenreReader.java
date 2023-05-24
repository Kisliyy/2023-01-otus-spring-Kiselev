package ru.otus.book_storage_batch.config.genres;

import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.database.builder.JdbcCursorItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.RowMapper;
import ru.otus.book_storage_batch.dto.genres.GenreInputDto;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;

@Configuration
public class GenreReader {

    private final static String SQL_QUERY = "SELECT * FROM genres";


    @Bean
    public JdbcCursorItemReader<GenreInputDto> genreItemReader(DataSource dataSource) {
        return new JdbcCursorItemReaderBuilder<GenreInputDto>()
                .name("genreItemReader")
                .dataSource(dataSource)
                .sql(SQL_QUERY)
                .rowMapper(new GenreInputRowMapper())
                .build();
    }

    private static class GenreInputRowMapper implements RowMapper<GenreInputDto> {

        @Override
        public GenreInputDto mapRow(ResultSet rs, int rowNum) throws SQLException {
            long id = rs.getLong("id");
            String name = rs.getString("name");
            return new GenreInputDto(id, name);
        }
    }
}
