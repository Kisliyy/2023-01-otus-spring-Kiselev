package ru.otus.book_storage.command.genre;

public interface GenreCommand {
    String getAll();

    String getById(Long id);
}
