package ru.otus.testing_students.service.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import ru.otus.testing_students.config.AppConfig;

import java.io.IOException;
import java.io.InputStream;

@Service
public class ResourceServiceImpl implements ResourceService {

    private final Resource resource;

    @Autowired
    public ResourceServiceImpl(AppConfig appConfig) {
        this.resource = new ClassPathResource(appConfig.getPathToQuestions());
    }

    @Override
    public InputStream getInputStreamResource() throws IOException {
        return resource.getInputStream();
    }
}
