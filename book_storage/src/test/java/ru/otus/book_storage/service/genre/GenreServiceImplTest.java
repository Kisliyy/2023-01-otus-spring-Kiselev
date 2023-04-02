package ru.otus.book_storage.service.genre;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.otus.book_storage.dao.genre.GenreRepository;
import ru.otus.book_storage.exceptions.NotFoundException;
import ru.otus.book_storage.models.Genre;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = GenreServiceImpl.class)
class GenreServiceImplTest {

    @MockBean
    private GenreRepository genreRepository;

    @Autowired
    private GenreService genreService;

    private final String textGenre = "novel";
    private final String genreId = "genre_id";

    @Test
    void getAllGenresTest() {
        List<Genre> genres = List.of(
                new Genre(),
                new Genre(),
                new Genre()
        );
        when(genreRepository.findAll()).thenReturn(genres);

        List<Genre> allGenres = genreService.getAll();
        assertNotNull(allGenres);
        assertFalse(allGenres.isEmpty());
        assertEquals(3, allGenres.size());
    }

    @Test
    void getByIdReturnGenreTest() {
        Genre findGenre = Genre.builder()
                .id(genreId)
                .name(textGenre)
                .build();

        when(genreRepository.findById(genreId)).thenReturn(Optional.of(findGenre));
        Genre genre = genreService.getById(genreId);
        assertEquals(findGenre, genre);
    }

    @Test
    void getByGenreReturnNullTest() {
        when(genreRepository.findById(anyString())).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> genreService.getById(anyString()));
        verify(genreRepository, times(1)).findById(anyString());
    }

}