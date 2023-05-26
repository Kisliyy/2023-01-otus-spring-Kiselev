package ru.otus.book_storage_batch.dto.books;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.otus.book_storage_batch.dto.authors.AuthorOutputDto;
import ru.otus.book_storage_batch.dto.comments.CommentOutputDto;
import ru.otus.book_storage_batch.dto.genres.GenreOutputDto;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookOutputDto {
    private String id;
    private String title;
    private AuthorOutputDto author;
    private GenreOutputDto genre;
    private List<CommentOutputDto> comments;
}
