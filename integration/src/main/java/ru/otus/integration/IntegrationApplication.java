package ru.otus.integration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import ru.otus.integration.services.order.OrderService;

@SpringBootApplication
public class IntegrationApplication {
    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(IntegrationApplication.class);
        OrderService orderService = run.getBean(OrderService.class);
        orderService.createOrders();
    }
}
