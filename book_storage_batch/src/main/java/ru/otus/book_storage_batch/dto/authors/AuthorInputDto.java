package ru.otus.book_storage_batch.dto.authors;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthorInputDto {
    private Long id;
    private String firstName;
    private String lastName;
}
