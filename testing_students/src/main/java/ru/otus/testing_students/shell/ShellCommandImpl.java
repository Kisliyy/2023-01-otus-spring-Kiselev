package ru.otus.testing_students.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;
import org.springframework.shell.standard.ShellOption;
import org.springframework.util.StringUtils;
import ru.otus.testing_students.service.testing.TestingService;

@ShellComponent
@RequiredArgsConstructor
public class ShellCommandImpl implements ShellCommand {

    private final TestingService testingService;

    private String firstName;
    private String lastName;

    @Override
    @ShellMethod(value = "Login command", key = {"login", "l"})
    public void login(@ShellOption(defaultValue = "Student", help = "First name") String firstName,
                      @ShellOption(defaultValue = "Student", help = "Last name") String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    @Override
    @ShellMethod(value = "Launch a survey command", key = {"survey", "s"})
    @ShellMethodAvailability(value = "isConductSurveyCommandAvailable")
    public void startConductSurvey() {
        testingService.conductSurvey(firstName, lastName);
    }

    @ShellMethod(value = "Logout command", key = {"logout", "lt"})
    public void logout() {
        this.firstName = null;
        this.lastName = null;
    }

    private Availability isConductSurveyCommandAvailable() {
        if (StringUtils.hasText(firstName) && StringUtils.hasText(lastName)) {
            return Availability.available();
        }
        return Availability.unavailable("Log in first");
    }
}
