package ru.otus.book_storage.service.genre;

import ru.otus.book_storage.models.Genre;

import java.util.List;

public interface GenreService {
    List<Genre> getAll();

    Genre getById(String id);
}
