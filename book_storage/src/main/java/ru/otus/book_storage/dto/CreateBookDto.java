package ru.otus.book_storage.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.otus.book_storage.models.Author;
import ru.otus.book_storage.models.Book;
import ru.otus.book_storage.models.Genre;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@NoArgsConstructor
@AllArgsConstructor
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CreateBookDto {
    @NotEmpty(message = "The title of the book cannot be empty")
    @NotNull(message = "The title of the book cannot be null")
    @Size(min = 2, max = 255, message = "The length of the book title should be from 1 to 255 characters")
    @JsonProperty("title")
    private String title;
    @JsonProperty("authorId")
    @NotNull(message = "Author id cannot be null!")
    @NotBlank(message = "Author id cannot be empty!")
    private String authorId;
    @JsonProperty("genreId")
    @NotNull(message = "Genre id cannot be null!")
    @NotBlank(message = "Genre id cannot be empty!")
    private String genreId;

    public Book toDomainObject() {
        Genre genre = new Genre(genreId);
        Author author = new Author(authorId);
        return Book.builder()
                .author(author)
                .genre(genre)
                .title(title)
                .build();
    }
}
