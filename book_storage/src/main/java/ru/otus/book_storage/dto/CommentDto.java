package ru.otus.book_storage.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentDto {
    private Long id;
    private String text;

    @Override
    public String toString() {
        return String.format(
                "Comment: id: %s text: %s",
                id,
                text);
    }
}
