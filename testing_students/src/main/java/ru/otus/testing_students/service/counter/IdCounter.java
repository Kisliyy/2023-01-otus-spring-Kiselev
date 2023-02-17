package ru.otus.testing_students.service.counter;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class IdCounter implements Counter {
    private long counter;

    public IdCounter(long initialValue) {
        this.counter = initialValue;
    }

    public IdCounter() {
        this.counter = 1;
    }

    @Override
    public long getNextValue() {
        return counter++;
    }
}
