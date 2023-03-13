package ru.otus.book_storage.service.author;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.book_storage.dao.author.AuthorDao;
import ru.otus.book_storage.models.Author;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {

    private final AuthorDao authorDao;

    @Override
    @Transactional
    public Author save(Author author) {
        return authorDao.save(author);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Author> getAll() {
        return authorDao.getAll();
    }

    @Override
    @Transactional
    public Author findByFirstNameAndLastName(String firstName, String lastName) {
        return authorDao
                .findByFirstNameAndLastName(firstName, lastName)
                .orElse(null);
    }
}
