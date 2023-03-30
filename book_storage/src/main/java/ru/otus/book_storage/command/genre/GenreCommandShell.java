package ru.otus.book_storage.command.genre;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.book_storage.models.Genre;
import ru.otus.book_storage.service.genre.GenreService;

import java.util.stream.Collectors;

@RequiredArgsConstructor
@ShellComponent
public class GenreCommandShell implements GenreCommand {

    private final GenreService genreService;

    @Override
    @ShellMethod(value = "Get all genres", key = "genres")
    public String getAll() {
        return genreService
                .getAll()
                .stream()
                .map(Genre::toString)
                .collect(Collectors.joining("\n"));
    }

    @Override
    @ShellMethod(value = "Gen genre by id", key = "genre by")
    public String getById(@ShellOption(help = "Genre id") String id) {
        Genre findGenre = genreService.getById(id);
        return findGenre.toString();
    }
}
