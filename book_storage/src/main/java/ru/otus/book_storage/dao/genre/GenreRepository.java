package ru.otus.book_storage.dao.genre;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.book_storage.models.Genre;

public interface GenreRepository extends JpaRepository<Genre, Long> {
}
