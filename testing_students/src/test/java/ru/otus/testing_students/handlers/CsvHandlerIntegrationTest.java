package ru.otus.testing_students.handlers;

import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import ru.otus.testing_students.config.AbstractTestConfig;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ComponentScan("ru.otus.testing_students")
class CsvHandlerIntegrationTest extends AbstractTestConfig {

    @Test
    void handleCsvFile() throws IOException {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(CsvHandlerIntegrationTest.class);
        CsvHandler csvHandler = context.getBean(CsvHandler.class);
        List<String> lines = csvHandler.handleCsvFile();
        assertNotNull(lines);
        assertEquals(6, lines.size());
    }
}