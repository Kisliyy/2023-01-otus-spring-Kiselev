package ru.otus.book_storage_batch.config.books;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.otus.book_storage_batch.config.listeners.AuthorCustomProcessListener;
import ru.otus.book_storage_batch.config.listeners.CommentCustomProcessListener;
import ru.otus.book_storage_batch.config.listeners.GenreCustomProcessListener;
import ru.otus.book_storage_batch.dto.authors.AuthorOutputDto;
import ru.otus.book_storage_batch.dto.books.BookInputDto;
import ru.otus.book_storage_batch.dto.books.BookOutputDto;
import ru.otus.book_storage_batch.dto.comments.CommentOutputDto;
import ru.otus.book_storage_batch.dto.genres.GenreOutputDto;
import ru.otus.book_storage_batch.utils.ObjectIdUtils;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class BookItemProcessorCustom {

    private final AuthorCustomProcessListener authorCustomProcessListener;
    private final GenreCustomProcessListener genreCustomProcessListener;

    private final CommentCustomProcessListener commentCustomProcessListener;


    @Bean
    public ItemProcessor<BookInputDto, BookOutputDto> bookProcessor() {
        return new BookItemProcessor();
    }

    private class BookItemProcessor implements ItemProcessor<BookInputDto, BookOutputDto> {

        @Override
        public BookOutputDto process(BookInputDto item) {
            long genreId = item.getGenreId();
            GenreOutputDto genre = genreCustomProcessListener.getByRelationId(genreId);
            long authorId = item.getAuthorId();
            AuthorOutputDto author = authorCustomProcessListener.getByRelationId(authorId);
            long id = item.getId();
            List<CommentOutputDto> comments = commentCustomProcessListener.getByRelationId(id);
            return BookOutputDto.builder()
                    .id(ObjectIdUtils.getObjectId())
                    .title(item.getTitle())
                    .author(author)
                    .genre(genre)
                    .comments(comments)
                    .build();
        }
    }
}
