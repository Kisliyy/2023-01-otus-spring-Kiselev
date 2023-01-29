package ru.otus.testing_students.handlers;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.otus.testing_students.service.ResourceService;
import ru.otus.testing_students.service.ResourceServiceImpl;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertThrows;

class CsvHandlerImplTest {

    private final ResourceService resourceService = Mockito.mock(ResourceServiceImpl.class);
    private final CsvHandler csvHandler = new CsvHandlerImpl(resourceService);

    @Test
    void handleCsvFileNullPointerExceptionTest() throws IOException {
        Mockito.when(resourceService.getInputStreamResource()).thenReturn(null);
        assertThrows(NullPointerException.class, csvHandler::handleCsvFile);
    }
}