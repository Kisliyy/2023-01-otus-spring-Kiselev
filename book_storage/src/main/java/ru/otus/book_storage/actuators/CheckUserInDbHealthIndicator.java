package ru.otus.book_storage.actuators;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.actuate.health.Status;
import org.springframework.stereotype.Component;
import ru.otus.book_storage.dao.user.UserRepository;
import ru.otus.book_storage.models.User;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CheckUserInDbHealthIndicator implements HealthIndicator {

    private final UserRepository userRepository;

    @Override
    public Health health() {
        List<User> allUsers = userRepository.findAll();
        if (allUsers.isEmpty()) {
            return Health
                    .down()
                    .status(Status.DOWN)
                    .withDetail("message", "There are no users in the database")
                    .build();
        } else {
            return Health
                    .up()
                    .status(Status.UP)
                    .withDetail("message", "Users are available in the database")
                    .build();
        }
    }
}
