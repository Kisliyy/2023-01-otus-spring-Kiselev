package ru.otus.testing_students.service;

import java.io.IOException;
import java.io.InputStream;

public interface ResourceService {
    InputStream getInputStreamResource() throws IOException;
}
