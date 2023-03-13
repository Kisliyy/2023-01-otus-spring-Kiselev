package ru.otus.book_storage.dao.author;

import ru.otus.book_storage.models.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorDao {

    Author save(Author author);

    List<Author> getAll();

    Optional<Author> findByFirstNameAndLastName(String firstName, String lastName);

}
