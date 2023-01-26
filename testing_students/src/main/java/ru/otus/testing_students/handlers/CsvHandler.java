package ru.otus.testing_students.handlers;

import java.io.IOException;
import java.util.List;

public interface CsvHandler {
    List<String> handleCsvFile() throws IOException;
}
