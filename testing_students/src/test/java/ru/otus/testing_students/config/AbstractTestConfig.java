package ru.otus.testing_students.config;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@TestPropertySource(locations = "classpath:application-test.yml")
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@SpringBootTest
public abstract class AbstractTestConfig {
}
