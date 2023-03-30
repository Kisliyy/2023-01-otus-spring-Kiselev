package ru.otus.book_storage.command.book;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.book_storage.models.Author;
import ru.otus.book_storage.models.Book;
import ru.otus.book_storage.models.Genre;
import ru.otus.book_storage.service.book.BookService;

import java.util.stream.Collectors;

@ShellComponent
@RequiredArgsConstructor
public class BookCommandShell implements BookCommand {

    private final BookService bookService;


    @Override
    @ShellMethod(value = "Add book", key = "add b")
    public String addBook(@ShellOption(help = "Title of the book") String title,
                          @ShellOption(help = "Author id") String authorId,
                          @ShellOption(help = "Genre id") String genreId) {

        Book book = Book.builder()
                .title(title)
                .genre(new Genre(genreId))
                .author(new Author(authorId))
                .build();
        Book persistBook = bookService.save(book);
        return persistBook.toString();
    }

    @Override
    @ShellMethod(value = "Get all book", key = {"books"})
    public String getAllBook() {
        return bookService
                .getAllBook()
                .stream()
                .map(Book::toString)
                .collect(Collectors.joining("\n"));
    }

    @Override
    @ShellMethod(value = "Delete book", key = "delete book")
    public String deleteBook(@ShellOption(help = "Book id") String id) {
        bookService.deleteById(id);
        return String.format("Book with id:%s successful deleted", id);
    }

    @Override
    @ShellMethod(value = "Get book by id", key = "get book")
    public String getBook(String id) {
        Book byId = bookService.getById(id);
        return byId.toString();
    }

    @Override
    @ShellMethod(value = "Update book", key = "upt b")
    public String updateBook(@ShellOption(help = "Book id") String id,
                             @ShellOption(help = "Update title of the book") String title,
                             @ShellOption(help = "Author id") String authorId,
                             @ShellOption(help = "Genre id") String genreId) {
        Book book = Book.builder()
                .id(id)
                .title(title)
                .author(new Author(authorId))
                .genre(new Genre(genreId))
                .build();
        bookService.updateBook(book);
        return "Book successful updated!";
    }

}

