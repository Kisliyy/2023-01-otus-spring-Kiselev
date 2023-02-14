package ru.otus.testing_students.answer.service;

import ru.otus.testing_students.answer.model.Answer;

import java.util.List;

public interface AnswerService {

    List<Answer> convertStringToAnswers(String lineAnswers);

    Answer getRightAnswer(List<Answer> answersOption, String lineRightAnswer);
}
