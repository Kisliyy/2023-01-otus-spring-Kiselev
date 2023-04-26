package ru.otus.book_storage.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.otus.book_storage.models.Book;

@NoArgsConstructor
@Data
public class BookResponseDto {
    private Long id;
    private String title;
    private String genreName;

    private String firstNameAuthor;
    private String lastNameAuthor;
    private int countComment;

    public BookResponseDto(Book book) {
        this.id = book.getId();
        this.title = book.getTitle();
        this.genreName = book.getGenre().getName();
        this.firstNameAuthor = book.getAuthor().getFirstName();
        this.lastNameAuthor = book.getAuthor().getLastName();
        this.countComment = book.getComments() == null ? 0 : book.getComments().size();
    }
}
