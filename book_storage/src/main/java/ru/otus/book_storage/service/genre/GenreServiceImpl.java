package ru.otus.book_storage.service.genre;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.book_storage.dao.genre.GenreDao;
import ru.otus.book_storage.models.Genre;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GenreServiceImpl implements GenreService {

    private final GenreDao genreDao;

    @Override
    @Transactional(readOnly = true)
    public List<Genre> getAll() {
        return genreDao.getAll();
    }

    @Override
    @Transactional
    public Genre getByGenre(String genre) {
        return genreDao
                .getByGenre(genre)
                .orElse(null);
    }

    @Override
    @Transactional
    public Genre save(Genre genre) {
        return genreDao.save(genre);
    }
}
