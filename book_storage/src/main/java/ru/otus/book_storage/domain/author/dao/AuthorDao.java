package ru.otus.book_storage.domain.author.dao;

import ru.otus.book_storage.domain.author.model.Author;

import java.util.List;

public interface AuthorDao {

    long save(Author author);

    List<Author> getAll();

    Author findByFirstNameAndLastName(String firstName, String lastName);

}
