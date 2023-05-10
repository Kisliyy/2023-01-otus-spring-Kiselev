package ru.otus.book_storage.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document
public class Book {
    @Id
    private String id;
    @Field(value = "title")
    private String title;

    @Field(value = "genre")
    private Genre genre;

    @Field(value = "author")
    private Author author;

    @DocumentReference
    private Set<Comment> comments = new HashSet<>();

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
