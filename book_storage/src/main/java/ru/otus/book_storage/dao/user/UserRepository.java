package ru.otus.book_storage.dao.user;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.book_storage.models.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}
