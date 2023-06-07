package ru.otus.book_storage.actuators;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@Component
@RequiredArgsConstructor
public class CheckDbHealthIndicator implements HealthIndicator {

    private final DataSource dataSource;

    @Override
    public Health health() {
        try (Connection connection = dataSource.getConnection()) {
            if (connection == null || connection.isClosed()) {
                return Health
                        .down()
                        .withDetail("message", "There is no connection to the database")
                        .build();
            }
            if (connection.isValid(5)) {
                return Health
                        .up()
                        .withDetail("message", "Connected")
                        .build();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Health
                .down()
                .withDetail("message", "Failed to get a connection to the database")
                .build();
    }
}
