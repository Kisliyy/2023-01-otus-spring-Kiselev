package ru.otus.book_storage.command.author;

public interface AuthorCommand {
    String getAllAuthors();

    String findById(String id);
}
