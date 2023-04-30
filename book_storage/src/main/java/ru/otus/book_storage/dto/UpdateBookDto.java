package ru.otus.book_storage.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UpdateBookDto {
    @NotNull(message = "Id cannot be null!")
    @NotBlank(message = "Id cannot be empty!")
    private String id;
    @NotEmpty(message = "The title of the book cannot be empty!")
    @NotNull(message = "The title of the book cannot be null!")
    @Size(min = 2, max = 255, message = "The length of the book title should be from 1 to 255 characters!")
    private String title;
    @NotNull(message = "Author id cannot be null!")
    @NotBlank(message = "Author id cannot be empty!")
    private String authorId;
    @NotNull(message = "Genre id cannot be null!")
    @NotBlank(message = "Genre id cannot be empty!")
    private String genreId;
}
