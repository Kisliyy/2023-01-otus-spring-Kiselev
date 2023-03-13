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
                          @ShellOption(help = "First name author") String firstName,
                          @ShellOption(help = "Last name author") String lastName,
                          @ShellOption(help = "Genre of the book") String genre) {

        Book book = Book.builder()
                .title(title)
                .genre(new Genre(null, genre))
                .author(new Author(null, firstName, lastName))
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
    public String deleteBook(@ShellOption(help = "Book id") Long id) {
        bookService.deleteById(id);
        return String.format("Book with id:%s successful deleted", id);
    }

    @Override
    @ShellMethod(value = "Get book by id", key = "get book")
    public String getBook(Long id) {
        Book byId = bookService.getById(id);
        return byId.toString();
    }

    @Override
    @ShellMethod(value = "Update book", key = "upt b")
    public String updateBook(@ShellOption(help = "Book id") Long id,
                             @ShellOption(help = "Update title of the book") String title,
                             @ShellOption(help = "Update first name author") String firstName,
                             @ShellOption(help = "Update last name author") String lastName,
                             @ShellOption(help = "Update genre of the book") String genre) {
        Book book = Book.builder()
                .id(id)
                .title(title)
                .author(new Author(null, firstName, lastName))
                .genre(new Genre(null, genre))
                .build();
        bookService.updateBook(book);
        return "Book successful updated!";
    }

}

