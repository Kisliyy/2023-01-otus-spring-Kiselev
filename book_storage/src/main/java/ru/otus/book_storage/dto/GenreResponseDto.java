package ru.otus.book_storage.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.otus.book_storage.models.Genre;

@Data
@NoArgsConstructor
public class GenreResponseDto {
    private Long id;
    private String name;

    public GenreResponseDto(Genre genre) {
        this.id = genre.getId();
        this.name = genre.getName();
    }
}
