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
        String existGenre = "novel";
        Optional<Genre> byGenre = genreDao.getByGenre(existGenre);
        assertFalse(byGenre.isEmpty());
        Genre genre = byGenre.get();
        assertEquals(existGenre, genre.getGenre());
        assertNotNull(genre.getId());
    }

    @Test
    void saveGenreSuccessfulTest() {
        String textGenre = "genre_test";
        Genre genre = Genre.builder()
                .genre(textGenre)
                .build();
        Genre persistGenre = genreDao.save(genre);
        Genre persist = testEntityManager.persist(genre);
        assertEquals(persist, persistGenre);
        assertNotNull(persistGenre.getId());
        assertEquals(textGenre, persistGenre.getGenre());
    }
}