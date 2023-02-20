package ru.otus.testing_students.student.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.testing_students.config.AppProperty;
import ru.otus.testing_students.student.model.Student;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {
    private final AppProperty appProperty;

    @Override
    public Student createStudent(String firstName, String lastName, int countWrightAnswerStudent) {
        boolean isPasses = countWrightAnswerStudent >= appProperty.getCountRightAnswer();
        return Student.builder()
                .firstName(firstName)
                .lastName(lastName)
                .countRightAnswers(countWrightAnswerStudent)
                .isPassed(isPasses)
                .build();
    }
}
