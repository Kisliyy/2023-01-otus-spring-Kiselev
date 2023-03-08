package ru.otus.book_storage.domain.genre.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Genre {
    private Long id;
    private String genre;

    public Genre(long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return String.format(
                "id: %s genre: %s",
                id,
                genre
        );
    }
}
