package ru.otus.testing_students.student.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.testing_students.config.AppConfig;
import ru.otus.testing_students.student.model.Student;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {

    private final AppConfig appConfig;

    @Override
    public Student createStudent(String firstName, String lastName, int countWrightAnswerStudent) {
        boolean isPasses = countWrightAnswerStudent > appConfig.getCountAnswer();
        return Student.builder()
                .firstName(firstName)
                .lastName(lastName)
                .countRightAnswers(countWrightAnswerStudent)
                .isPassed(isPasses)
                .build();
    }
}
