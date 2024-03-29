package ru.otus.book_storage.dao.author;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.book_storage.models.Author;

public interface AuthorRepository extends JpaRepository<Author, Long> {
}
