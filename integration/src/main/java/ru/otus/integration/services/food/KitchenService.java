package ru.otus.integration.services.food;

import ru.otus.integration.models.Food;
import ru.otus.integration.models.Order;

public interface KitchenService {
    Food prepareFood(Order order);
}
