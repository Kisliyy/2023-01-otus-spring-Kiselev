package ru.otus.book_storage.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.otus.book_storage.models.Author;

@Data
@NoArgsConstructor
public class AuthorResponseDto {
    private String id;
    private String firstName;
    private String lastName;

    public AuthorResponseDto(Author author) {
        this.id = author.getId();
        this.firstName = author.getFirstName();
        this.lastName = author.getLastName();
    }
}
