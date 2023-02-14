package ru.otus.testing_students.question.service;

import ru.otus.testing_students.question.model.Question;

import java.util.List;

public interface QuestionService {

    List<Question> convertStringsToQuestions(List<String> lines);

    int getCountRightAnswers(List<Question> questions, List<String> answers);

}
