package ru.otus.integration.services.bar;

import ru.otus.integration.models.Food;
import ru.otus.integration.models.Order;

public interface BarService {

    Food pourDrink(Order order);
}
