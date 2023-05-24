package ru.otus.book_storage_batch.dto.comments;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CommentOutputDto {
    private String id;
    private String text;
}
