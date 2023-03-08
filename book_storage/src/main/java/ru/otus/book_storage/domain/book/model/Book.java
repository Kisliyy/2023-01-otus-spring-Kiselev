package ru.otus.book_storage.domain.book.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.otus.book_storage.domain.author.model.Author;
import ru.otus.book_storage.domain.genre.model.Genre;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Book {
    private Long id;
    private String title;
    private Genre genre;
    private Author author;

    public Book(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return String.format(
                "id: %s, book: %s, genre: %s, author: %s %s",
                id,
                title,
                genre.getGenre(),
                author.getFirstName(),
                author.getLastName()
        );
    }
}
