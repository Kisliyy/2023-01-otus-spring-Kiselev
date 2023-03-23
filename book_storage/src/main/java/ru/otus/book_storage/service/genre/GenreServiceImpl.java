package ru.otus.book_storage.service.genre;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.book_storage.dao.genre.GenreDao;
import ru.otus.book_storage.exceptions.NotFoundException;
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
    public Genre getById(Long id) {
        return genreDao
                .getById(id)
                .orElseThrow(() -> new NotFoundException("Genre not found by id: " + id));
    }
}
