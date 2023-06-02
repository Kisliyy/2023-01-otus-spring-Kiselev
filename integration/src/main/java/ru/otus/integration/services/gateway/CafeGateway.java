package ru.otus.integration.services.gateway;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;
import ru.otus.integration.models.Food;
import ru.otus.integration.models.Order;

import java.util.List;

@MessagingGateway
public interface CafeGateway {

    @Gateway(requestChannel = "orderChannel", replyChannel = "mergeFoodChannel")
    List<Food> processFood(List<Order> order);

}
