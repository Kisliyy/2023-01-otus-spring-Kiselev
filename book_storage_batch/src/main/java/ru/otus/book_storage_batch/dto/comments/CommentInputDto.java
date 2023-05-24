package ru.otus.book_storage_batch.dto.comments;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentInputDto {
    private long id;
    private String text;
    private long bookId;
}
