package ru.otus.testing_students.service.resource;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import ru.otus.testing_students.config.AppProperty;

import java.io.IOException;
import java.io.InputStream;

@Service
public class ResourceServiceImpl implements ResourceService {

    private final Resource resource;

    public ResourceServiceImpl(AppProperty appConfig) {
        this.resource = new ClassPathResource(appConfig.getPathData());
    }

    @Override
    public InputStream getInputStreamResource() throws IOException {
        return resource.getInputStream();
    }
}
