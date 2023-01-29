package ru.otus.testing_students.handlers;

import ru.otus.testing_students.service.ResourceService;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class CsvHandlerImpl implements CsvHandler {

    private final String delimiter = ",";
    private final ResourceService resourceService;

    public CsvHandlerImpl(ResourceService resourceService) {
        this.resourceService = resourceService;
    }


    @Override
    public List<String> handleCsvFile() throws IOException {
        List<List<String>> records = new ArrayList<>(0);
        try (InputStream source = resourceService.getInputStreamResource();
             Scanner scanner = new Scanner(source)) {
            while (scanner.hasNextLine()) {
                records.add(getRecordFromLine(scanner.nextLine()));
            }
            return records
                    .stream()
                    .map(record -> String.join(" ", record))
                    .collect(Collectors.toList());
        }
    }

    private List<String> getRecordFromLine(String line) {
        List<String> values = new ArrayList<>(0);
        try (Scanner rowScanner = new Scanner(line)) {
            rowScanner.useDelimiter(delimiter);
            while (rowScanner.hasNext()) {
                values.add(rowScanner.next());
            }
        }
        return values;
    }
}
