package ru.otus.book_storage.dao.book;

import ru.otus.book_storage.models.Book;

import java.util.List;
import java.util.Optional;

public interface BookDao {
    Book save(Book book);

    void deleteById(long id);

    Optional<Book> getById(long id);

    List<Book> getAll();

    void update(Book book);

}
