package ru.otus.book_storage.command.author;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.otus.book_storage.models.Author;
import ru.otus.book_storage.service.author.AuthorService;

import java.util.stream.Collectors;

@ShellComponent
@RequiredArgsConstructor
public class AuthorCommandShell implements AuthorCommand {

    private final AuthorService authorService;

    @Override
    @ShellMethod(value = "Get all authors", key = "authors")
    public String getAllAuthors() {
        return authorService
                .getAll()
                .stream()
                .map(Author::toString)
                .collect(Collectors.joining("\n"));
    }
}
