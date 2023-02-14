package ru.otus.testing_students.handlers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.otus.testing_students.exceptions.HandleException;
import ru.otus.testing_students.service.resource.ResourceService;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Service
public class CsvHandlerImpl implements CsvHandler {

    private final ResourceService resourceService;

    @Autowired
    public CsvHandlerImpl(ResourceService resourceService) {
        this.resourceService = resourceService;
    }

    @Override
    public List<String> handleCsvFile() {
        List<String> records = new ArrayList<>(0);
        try (InputStream source = resourceService.getInputStreamResource();
             Scanner scanner = new Scanner(source)) {
            while (scanner.hasNextLine()) {
                records.add(scanner.nextLine());
            }
            return records;
        } catch (IOException e) {
            throw new HandleException(e);
        }
    }

}
