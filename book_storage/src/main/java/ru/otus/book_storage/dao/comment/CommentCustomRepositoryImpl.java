package ru.otus.book_storage.dao.comment;

import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import ru.otus.book_storage.models.Comment;

@RequiredArgsConstructor
public class CommentCustomRepositoryImpl implements CommentCustomRepository {

    private final MongoTemplate mongoTemplate;

    @Override
    public void deleteCommentsByBookId(String id) {
        var query = Query.query(Criteria.where("book.$id").is(new ObjectId(id)));
        mongoTemplate.remove(query, Comment.class);
    }
}
