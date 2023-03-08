package ru.otus.book_storage.domain.book.dao;

import ru.otus.book_storage.domain.book.model.Book;

import java.util.List;

public interface BookDao {
    void save(Book book);

    void deleteById(long id);

    Book getById(long id);

    List<Book> getAll();

    void update(Book book);

}
