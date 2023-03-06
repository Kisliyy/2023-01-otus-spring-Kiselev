package ru.otus.book_storage.helper;

import org.springframework.jdbc.support.KeyHolder;

public interface KeyHolderFactory {
    KeyHolder newKeyHolder();
}
