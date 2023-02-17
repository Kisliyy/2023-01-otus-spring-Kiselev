package ru.otus.testing_students.service.message;

import ru.otus.testing_students.question.model.Question;
import ru.otus.testing_students.student.model.Student;

public interface MessageSourceService {

    String getLocalizationQuestionMessage(Question question);

    String getLocalizationStudentMessage(Student student);

    String getLocalizationMessage(String code);

    String getLocalizationMessage(String code, Object[] args);
}
