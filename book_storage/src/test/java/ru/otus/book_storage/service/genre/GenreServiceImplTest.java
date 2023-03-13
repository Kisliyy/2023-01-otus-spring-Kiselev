package ru.otus.book_storage.service.genre;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.otus.book_storage.dao.genre.GenreDao;
import ru.otus.book_storage.models.Genre;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = GenreServiceImpl.class)
class GenreServiceImplTest {

    @MockBean
    private GenreDao genreDao;

    @Autowired
    private GenreService genreService;

    private final String textGenre = "novel";
    private final long genreId = 2;

    @Test
    void getAllGenresTest() {
        List<Genre> genres = List.of(
                new Genre(),
                new Genre(),
                new Genre()
        );
        when(genreDao.getAll()).thenReturn(genres);

        List<Genre> allGenres = genreService.getAll();
        assertNotNull(allGenres);
        assertFalse(allGenres.isEmpty());
        assertEquals(3, allGenres.size());
    }

    @Test
    void getByGenreReturnGenreTest() {
        Genre findGenre = Genre.builder()
                .id(genreId)
                .genre(textGenre)
                .build();

        when(genreDao.getByGenre(textGenre)).thenReturn(Optional.of(findGenre));

        Genre genre = genreService.getByGenre(textGenre);
        assertEquals(findGenre, genre);
    }

    @Test
    void getByGenreReturnNullTest() {
        when(genreDao.getByGenre(anyString())).thenReturn(Optional.empty());
        Genre genre = genreService.getByGenre(anyString());
        assertNull(genre);
        verify(genreDao, times(1)).getByGenre(anyString());
    }

    @Test
    void saveGenreSuccessfulTest() {
        Genre savedGenre = Genre.builder()
                .genre(textGenre)
                .build();
        Genre persistGenre = Genre.builder()
                .id(genreId)
                .genre(textGenre)
                .build();
        when(genreDao.save(savedGenre)).thenReturn(persistGenre);
        Genre genre = genreService.save(savedGenre);
        assertEquals(persistGenre, genre);
    }
}