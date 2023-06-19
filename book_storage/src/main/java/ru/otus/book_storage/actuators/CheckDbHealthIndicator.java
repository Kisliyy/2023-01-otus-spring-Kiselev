package ru.otus.book_storage.actuators;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@Component
@RequiredArgsConstructor
@Slf4j
public class CheckDbHealthIndicator implements HealthIndicator {

    private final DataSource dataSource;

    @Override
    public Health health() {
        Health health = null;
        try (Connection connection = dataSource.getConnection()) {
            if (connection == null || connection.isClosed()) {
                health = Health
                        .down()
                        .withDetail("message", "There is no connection to the database")
                        .build();
            }
            if (connection.isValid(5)) {
                health = Health
                        .up()
                        .withDetail("message", "Connected")
                        .build();
            }
        } catch (SQLException e) {
            log.error(e.getMessage());
            health = Health
                    .down()
                    .withDetail("message", "Failed to get a connection to the database")
                    .build();
        }
        return health;
    }
}
