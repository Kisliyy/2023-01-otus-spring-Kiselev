package ru.otus.book_storage.dao.genre;

import ru.otus.book_storage.models.Genre;

import java.util.List;
import java.util.Optional;

public interface GenreDao {

    List<Genre> getAll();

    Optional<Genre> getById(long id);
}
