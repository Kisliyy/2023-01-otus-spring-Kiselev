package ru.otus.book_storage.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.otus.book_storage.models.Comment;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentResponseDto {
    private String id;
    private String text;

    public CommentResponseDto(Comment comment) {
        this.id = comment.getId();
        this.text = comment.getText();
    }

    @Override
    public String toString() {
        return String.format(
                "Comment: id: %s text: %s",
                id,
                text);
    }
}
