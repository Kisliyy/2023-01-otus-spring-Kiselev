package ru.otus.book_storage.service.author;

import ru.otus.book_storage.models.Author;

import java.util.List;

public interface AuthorService {
    List<Author> getAll();

    Author findById(Long id);
}
