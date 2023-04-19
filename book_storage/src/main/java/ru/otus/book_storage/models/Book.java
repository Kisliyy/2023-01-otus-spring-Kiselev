package ru.otus.book_storage.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "books")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "title")
    private String title;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "genre_id")
    private Genre genre;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "author_id")
    private Author author;

    @OneToMany(fetch = FetchType.LAZY,
            mappedBy = "book",
            orphanRemoval = true,
            cascade = CascadeType.REMOVE)
    private List<Comment> comments;

    @Override
    public String toString() {
        return String.format(
                "id: %s, book: %s, genre: %s, author: %s %s",
                id,
                title,
                genre.getName(),
                author.getFirstName(),
                author.getLastName()
        );
    }
}
