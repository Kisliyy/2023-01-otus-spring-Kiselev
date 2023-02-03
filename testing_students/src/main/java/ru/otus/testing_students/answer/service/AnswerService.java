package ru.otus.testing_students.answer.service;

import ru.otus.testing_students.answer.model.Answer;

import java.util.List;

public interface AnswerService {

    List<Answer> getAnswer(String lineAnswers);

    Answer getWrightAnswer(List<Answer> answers, String lineWrightAnswer);
}
