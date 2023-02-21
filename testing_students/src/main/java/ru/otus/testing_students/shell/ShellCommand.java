package ru.otus.testing_students.shell;

import java.io.IOException;

public interface ShellCommand {

    void login(String firstName, String lastName);

    void logout();

    void startConductSurvey() throws IOException;
}
