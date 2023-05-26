package ru.otus.book_storage_batch.config.listeners;

import org.springframework.batch.core.ItemProcessListener;
import org.springframework.stereotype.Component;
import ru.otus.book_storage_batch.dto.comments.CommentInputDto;
import ru.otus.book_storage_batch.dto.comments.CommentOutputDto;

import java.util.*;

@Component
public class CommentCustomProcessListener implements ItemProcessListener<CommentInputDto, CommentOutputDto> {

    private final Map<Long, List<CommentOutputDto>> comments = new HashMap<>();


    public List<CommentOutputDto> getByRelationId(Long id) {
        List<CommentOutputDto> commentOutputDtos = comments.get(id);
        return Objects.requireNonNullElse(commentOutputDtos, Collections.emptyList());
    }

    @Override
    public void beforeProcess(CommentInputDto item) {

    }

    @Override
    public void afterProcess(CommentInputDto item, CommentOutputDto result) {
        long bookId = item.getBookId();
        List<CommentOutputDto> commentOutputDtos = comments.get(bookId);
        if (commentOutputDtos == null) {
            commentOutputDtos = new ArrayList<>();
        }
        commentOutputDtos.add(result);
        comments.put(bookId, commentOutputDtos);
    }

    @Override
    public void onProcessError(CommentInputDto item, Exception e) {

    }
}
