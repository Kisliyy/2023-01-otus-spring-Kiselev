package ru.otus.book_storage.service.genre;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.book_storage.dao.genre.GenreRepository;
import ru.otus.book_storage.exceptions.NotFoundException;
import ru.otus.book_storage.models.Genre;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GenreServiceImpl implements GenreService {

    private final GenreRepository genreRepository;

    @Override
    public List<Genre> getAll() {
        return genreRepository.findAll();
    }

    @Override
    public Genre getById(Long id) {
        return genreRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException("Genre not found by id: " + id));
    }
}
