package ru.otus.book_storage.service.author;

import ru.otus.book_storage.models.Author;

import java.util.List;

public interface AuthorService {
    Author save(Author author);

    List<Author> getAll();

    Author findByFirstNameAndLastName(String firstName, String lastName);
}
