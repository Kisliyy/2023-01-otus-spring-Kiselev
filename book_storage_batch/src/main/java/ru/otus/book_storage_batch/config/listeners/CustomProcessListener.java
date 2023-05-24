package ru.otus.book_storage_batch.config.listeners;

import org.springframework.batch.core.ItemProcessListener;

public interface CustomProcessListener<ID, T, S> extends ItemProcessListener<T, S> {
    S getByRelationId(ID id);
}
