package ru.otus.book_storage.command.genre;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.otus.book_storage.domain.genre.dao.GenreDao;
import ru.otus.book_storage.domain.genre.model.Genre;

import java.util.stream.Collectors;

@RequiredArgsConstructor
@ShellComponent
public class GenreCommandShell implements GenreCommand {

    private final GenreDao genreDao;

    @Override
    @ShellMethod(value = "Get all genres", key = "genres")
    public String getAll() {
        return genreDao
                .getAll()
                .stream()
                .map(Genre::toString)
                .collect(Collectors.joining("\n"));
    }
}
