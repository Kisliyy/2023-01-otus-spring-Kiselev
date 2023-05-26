package ru.otus.book_storage_batch.dto.books;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class BookInputDto {
    private long id;
    private String title;
    private long genreId;
    private long authorId;
}
