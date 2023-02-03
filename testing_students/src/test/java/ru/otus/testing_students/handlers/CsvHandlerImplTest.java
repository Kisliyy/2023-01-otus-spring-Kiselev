package ru.otus.testing_students.handlers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;

@PropertySource("classpath:application-test.yml")
class CsvHandlerImplTest {

    @Autowired
    private CsvHandler csvHandler;


//    @Test
//    void handleCsvFileNullPointerExceptionTest() throws IOException {
//        Mockito.when(resourceService.getInputStreamResource()).thenReturn(null);
//        assertThrows(NullPointerException.class, csvHandler::handleCsvFile);
//    }

    @Test
    void handleCsvFile() throws IOException {
        List<String> strings = csvHandler.handleCsvFile();
    }
}