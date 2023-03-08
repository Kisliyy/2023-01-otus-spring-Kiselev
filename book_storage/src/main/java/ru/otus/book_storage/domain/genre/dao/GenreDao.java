package ru.otus.book_storage.domain.genre.dao;

import ru.otus.book_storage.domain.genre.model.Genre;

import java.util.List;

public interface GenreDao {

    List<Genre> getAll();

    Genre getByGenre(String genre);

    long save(Genre genre);
}
