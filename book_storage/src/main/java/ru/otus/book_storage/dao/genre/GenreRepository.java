package ru.otus.book_storage.dao.genre;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.book_storage.models.Genre;

public interface GenreRepository extends MongoRepository<Genre, String> {
}
