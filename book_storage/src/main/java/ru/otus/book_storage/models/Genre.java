package ru.otus.book_storage.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document
public class Genre {
    @Id
    private String id;
    @Field(value = "name")
    private String name;

    public Genre(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return String.format(
                "id: %s genre: %s",
                id,
                name
        );
    }
}
