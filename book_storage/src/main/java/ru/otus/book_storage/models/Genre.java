package ru.otus.book_storage.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "genres")
public class Genre {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "genre", nullable = false, unique = true)
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
