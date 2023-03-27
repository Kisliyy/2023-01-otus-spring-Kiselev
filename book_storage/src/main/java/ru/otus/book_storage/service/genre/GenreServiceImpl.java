package ru.otus.book_storage.service.genre;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.book_storage.dao.genre.GenreDao;
import ru.otus.book_storage.exceptions.NotFoundException;
import ru.otus.book_storage.models.Genre;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GenreServiceImpl implements GenreService {

    private final GenreDao genreDao;

    @Override
    public List<Genre> getAll() {
        return genreDao.findAll();
    }

    @Override
    public Genre getById(Long id) {
        return genreDao
                .findById(id)
                .orElseThrow(() -> new NotFoundException("Genre not found by id: " + id));
    }
}
