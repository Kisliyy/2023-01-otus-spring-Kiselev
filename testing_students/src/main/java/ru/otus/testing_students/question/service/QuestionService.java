package ru.otus.testing_students.question.service;

import ru.otus.testing_students.question.model.Question;

import java.io.IOException;
import java.util.List;

public interface QuestionService {

    List<Question> getQuestionsWithAnswers() throws IOException;

    int getCountRightAnswers(List<Question> questions, List<String> answers);

}
