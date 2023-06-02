package ru.otus.integration.services.bar;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.otus.integration.models.Food;
import ru.otus.integration.models.Order;
import ru.otus.integration.models.ProductType;

@Service
@Slf4j
public class BarServiceImpl implements BarService {
    @Override
    public Food pourDrink(Order order) {
        try {
            if (order.getProductType().equals(ProductType.DRINK)) {
                String itemName = order.getItemName();
                log.info("Start pour a drink {}", itemName);
                Thread.sleep(1000);
                log.info("End pour a drink {}", itemName);
                return new Food(itemName);
            } else {
                throw new RuntimeException(String.format("You will not be able to make a drink from the resulting object %s", order));
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
