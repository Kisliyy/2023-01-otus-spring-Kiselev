package ru.otus.book_storage.dao.book;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.book_storage.models.Book;

public interface BookDao extends JpaRepository<Book, Long> {
}
