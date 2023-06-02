package ru.otus.integration.services.order;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.otus.integration.models.Food;
import ru.otus.integration.models.Order;
import ru.otus.integration.models.ProductType;
import ru.otus.integration.services.gateway.CafeGateway;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderServiceImpl implements OrderService {

    private final CafeGateway cafeGateway;

    @Override
    public void createOrders() {
        ForkJoinPool forkJoinPool = ForkJoinPool.commonPool();
        for (int i = 0; i < 10; i++) {
            delay();
            forkJoinPool.execute(() -> {
                List<Order> orders = getOrders();
                log.info("New orders: {}", orders);
                List<Food> foods = cafeGateway.processFood(orders);
                log.info("Foods ready: {}", foods);
            });
        }
    }


    private List<Order> getOrders() {
        ArrayList<Order> orders = new ArrayList<>();

        orders.add(new Order("water", ProductType.DRINK));
        orders.add(new Order("chicken", ProductType.FOOD));
        orders.add(new Order("cola", ProductType.DRINK));
        orders.add(new Order("tequila", ProductType.DRINK));
        orders.add(new Order("burger", ProductType.FOOD));
        orders.add(new Order("rice", ProductType.FOOD));

        return orders;
    }

    private void delay() {
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }
}
