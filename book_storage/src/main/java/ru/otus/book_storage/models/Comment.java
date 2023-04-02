package ru.otus.book_storage.models;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@NoArgsConstructor
@EqualsAndHashCode
@AllArgsConstructor
@Builder
@Document
public class Comment {

    @Id
    private String id;

    @Field(value = "text")
    private String text;

    @DBRef
    private Book book;

    @Override
    public String toString() {
        return String.format(
                "Comment: id: %s text: %s",
                id,
                text);
    }
}
