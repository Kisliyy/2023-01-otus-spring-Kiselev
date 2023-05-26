package ru.otus.book_storage_batch.dto.genres;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GenreInputDto {
    private long id;
    private String name;
}
