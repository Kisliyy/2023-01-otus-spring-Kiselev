package ru.otus.testing_students.service.message;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import ru.otus.testing_students.answer.model.Answer;
import ru.otus.testing_students.config.AppProperty;
import ru.otus.testing_students.question.model.Question;
import ru.otus.testing_students.student.model.Student;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MessageSourceServiceImpl implements MessageSourceService {

    private final MessageSource messageSource;
    private final AppProperty appProperty;

    @Override
    public String getLocalizationQuestionMessage(Question question) {
        String localizationQuestionMessage = getLocalizationMessage("print.question", new Object[]{question.getQuestion()});
        List<Answer> answers = question.getAnswers();
        if (answers.size() > 1) {
            String localizationAnswerMessage = getLocalizationMessage("print.answer", answers.toArray());
            return localizationQuestionMessage +
                    localizationAnswerMessage +
                    answers
                            .stream()
                            .map(Answer::getAnswer)
                            .collect(Collectors.joining(", "));
        }
        return localizationQuestionMessage;
    }

    @Override
    public String getLocalizationStudentMessage(Student student) {
        Object[] args = new Object[]{student.getFirstName(), student.getLastName(), student.getCountRightAnswers(), student.isPassed()};
        return getLocalizationMessage("print.student", args);
    }

    @Override
    public String getLocalizationMessage(String code) {
        return getLocalizationMessage(code, null);
    }

    @Override
    public String getLocalizationMessage(String code, Object[] args) {
        return messageSource.getMessage(code, args, appProperty.getLocale());
    }
}
