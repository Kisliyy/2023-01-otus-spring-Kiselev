package ru.otus.integration.services.food;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.otus.integration.models.Food;
import ru.otus.integration.models.Order;
import ru.otus.integration.models.ProductType;

@Service
@Slf4j
public class KitchenServiceImpl implements KitchenService {

    @Override
    public Food prepareFood(Order order) {
        try {
            if (order.getProductType().equals(ProductType.FOOD)) {
                String itemName = order.getItemName();
                log.info("Start prepare food {}", itemName);
                Thread.sleep(2000);
                log.info("End prepare food {}", itemName);
                return new Food(itemName);
            } else {
                throw new RuntimeException(String.format("You will not be able to cook food from the resulting object %s", order));
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
