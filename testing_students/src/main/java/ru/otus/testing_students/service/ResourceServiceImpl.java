package ru.otus.testing_students.service;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.io.InputStream;

public class ResourceServiceImpl implements ResourceService {

    private final Resource resource;

    public ResourceServiceImpl(String pathToQuestions) {
        this.resource = new ClassPathResource(pathToQuestions);
    }

    @Override
    public InputStream getInputStreamResource() throws IOException {
        return resource.getInputStream();
    }
}
