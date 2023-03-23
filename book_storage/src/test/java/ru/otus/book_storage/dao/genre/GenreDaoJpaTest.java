package ru.otus.book_storage.dao.genre;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.otus.book_storage.models.Genre;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Import(GenreDaoJpa.class)
class GenreDaoJpaTest {

    @Autowired
    private GenreDao genreDao;

    @Autowired
    private TestEntityManager testEntityManager;

    @Test
    void getAllGenresTest() {
        List<Genre> allExistGenres = genreDao.getAll();
        assertNotNull(allExistGenres);
        assertFalse(allExistGenres.isEmpty());
        assertEquals(3, allExistGenres.size());
        assertTrue(allExistGenres.stream().allMatch(Objects::nonNull));
    }

    @Test
    void getByGenreReturnGenreTest() {
        Long existGenreId = 1L;
        Optional<Genre> byGenre = genreDao.getById(existGenreId);
        assertFalse(byGenre.isEmpty());
        Genre actualGenre = byGenre.get();
        var expectedGenre = testEntityManager.find(Genre.class, existGenreId);
        assertNotNull(actualGenre.getId());
        assertEquals(expectedGenre, actualGenre);
        assertEquals(existGenreId, actualGenre.getId());
    }

    @Test
    void getByGenreReturnOptionalEmptyTest() {
        long nonExistentGenreId = 50000L;
        Optional<Genre> byGenre = genreDao.getById(nonExistentGenreId);
        assertTrue(byGenre.isEmpty());
    }

}