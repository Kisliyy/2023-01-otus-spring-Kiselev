package ru.otus.book_storage.dao.book;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import ru.otus.book_storage.models.Book;

public interface BookRepository extends ReactiveMongoRepository<Book, String> {
}
