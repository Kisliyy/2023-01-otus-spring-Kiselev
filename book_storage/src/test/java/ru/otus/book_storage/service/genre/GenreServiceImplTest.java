package ru.otus.book_storage.service.genre;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.otus.book_storage.dao.genre.GenreDao;
import ru.otus.book_storage.exceptions.NotFoundException;
import ru.otus.book_storage.models.Genre;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
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
    void getByIdReturnGenreTest() {
        Genre findGenre = Genre.builder()
                .id(genreId)
                .genre(textGenre)
                .build();

        when(genreDao.getById(genreId)).thenReturn(Optional.of(findGenre));
        Genre genre = genreService.getById(genreId);
        assertEquals(findGenre, genre);
    }

    @Test
    void getByGenreReturnNullTest() {
        when(genreDao.getById(anyLong())).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> genreService.getById(anyLong()));
        verify(genreDao, times(1)).getById(anyLong());
    }

}