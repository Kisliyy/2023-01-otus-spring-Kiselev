package ru.otus.book_storage.command.author;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.otus.book_storage.domain.author.dao.AuthorDao;
import ru.otus.book_storage.domain.author.model.Author;

import java.util.stream.Collectors;

@ShellComponent
@RequiredArgsConstructor
public class AuthorCommandShell implements AuthorCommand {

    private final AuthorDao authorDao;

    @Override
    @ShellMethod(value = "Get all authors", key = "authors")
    public String getAllAuthors() {
        return authorDao
                .getAll()
                .stream()
                .map(Author::toString)
                .collect(Collectors.joining("\n"));
    }
}
