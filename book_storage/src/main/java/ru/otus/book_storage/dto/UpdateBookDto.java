package ru.otus.book_storage.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.otus.book_storage.models.Author;
import ru.otus.book_storage.models.Book;
import ru.otus.book_storage.models.Genre;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UpdateBookDto {
    private Long id;
    @NotEmpty(message = "The title of the book cannot be empty!")
    @NotNull(message = "The title of the book cannot be null!")
    @Size(min = 2, max = 255, message = "The length of the book title should be from 1 to 255 characters!")
    private String title;
    private long authorId;
    private long genreId;

    public Book toDomainObject() {
        Author author = new Author(authorId);
        Genre genre = new Genre(genreId);
        return Book.builder()
                .id(id)
                .title(title)
                .author(author)
                .genre(genre)
                .build();
    }
}
