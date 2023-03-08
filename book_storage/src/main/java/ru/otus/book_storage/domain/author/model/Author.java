package ru.otus.book_storage.domain.author.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Author {
    private Long id;
    private String firstName;
    private String lastName;

    public Author(long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return String.format(
                "id: %s author: %s %s",
                id,
                firstName,
                lastName
        );
    }
}
