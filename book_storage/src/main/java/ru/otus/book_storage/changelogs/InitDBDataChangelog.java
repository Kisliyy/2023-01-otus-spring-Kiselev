package ru.otus.book_storage.changelogs;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.mongodb.client.MongoDatabase;
import ru.otus.book_storage.dao.author.AuthorRepository;
import ru.otus.book_storage.dao.book.BookRepository;
import ru.otus.book_storage.dao.comment.CommentRepository;
import ru.otus.book_storage.dao.genre.GenreRepository;
import ru.otus.book_storage.models.Author;
import ru.otus.book_storage.models.Book;
import ru.otus.book_storage.models.Comment;
import ru.otus.book_storage.models.Genre;

import java.util.List;

@ChangeLog(order = "001")
public class InitDBDataChangelog {

    private Author pushkin;
    private Author remark;
    private Author turgenev;

    private Genre poem;
    private Genre novel;
    private Genre comedy;

    private Book romanAndLudmila;


    @ChangeSet(order = "000", id = "dropDB", author = "kiselev")
    public void dropDB(MongoDatabase database) {
        database.drop();
    }

    @ChangeSet(order = "001", id = "initAuthors", author = "kiselev")
    public void initAuthors(AuthorRepository authorRepository) {
        pushkin = authorRepository.save(new Author("Aleksandr", "Pushkin"));
        remark = authorRepository.save(new Author("Erich", "Remark"));
        turgenev = authorRepository.save(new Author("Ivan", "Turgenev"));
    }

    @ChangeSet(order = "002", id = "initGenres", author = "kiselev")
    public void initGenres(GenreRepository genreRepository) {
        poem = genreRepository.save(new Genre(null, "poem"));
        novel = genreRepository.save(new Genre(null, "novel"));
        comedy = genreRepository.save(new Genre(null, "comedy"));
    }

    @ChangeSet(order = "003", id = "initBooks", author = "kiselev")
    public void initBooks(BookRepository bookRepository) {
        romanAndLudmila = Book.builder()
                .author(pushkin)
                .genre(poem)
                .title("Roman and Ludmila")
                .build();

        Book threeComrades = Book.builder()
                .author(remark)
                .genre(novel)
                .title("Three comrades")
                .build();

        Book cherryOrchard = Book.builder()
                .author(turgenev)
                .title("Cherry Orchard")
                .genre(comedy)
                .build();
        bookRepository.saveAll(List.of(romanAndLudmila, threeComrades, cherryOrchard));
    }

    @ChangeSet(order = "004", id = "initComments", author = "kiselev")
    public void initComments(CommentRepository commentRepository) {
        Comment firstComment = commentRepository.save(new Comment(null, "Good book!", romanAndLudmila));
        Comment secondComment = commentRepository.save(new Comment(null, "Great book!", romanAndLudmila));
        Comment thirdComment = commentRepository.save(new Comment(null, "Not bad!", romanAndLudmila));
    }
}
