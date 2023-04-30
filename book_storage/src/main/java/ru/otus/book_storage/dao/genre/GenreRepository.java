package ru.otus.book_storage.dao.genre;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import ru.otus.book_storage.models.Genre;

public interface GenreRepository extends ReactiveMongoRepository<Genre, String> {
}
