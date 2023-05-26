package ru.otus.book_storage_batch.dto.authors;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthorOutputDto {
    private String id;
    private String firstName;
    private String lastName;
}
