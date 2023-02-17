package ru.otus.testing_students.handlers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.otus.testing_students.config.AbstractTestConfig;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


class CsvHandlerIntegrationTest extends AbstractTestConfig {

    @Autowired
    private CsvHandler csvHandler;

    @Test
    void handleCsvFile() {
        List<String> lines = csvHandler.handleCsvFile();
        assertNotNull(lines);
        assertEquals(6, lines.size());
    }
}