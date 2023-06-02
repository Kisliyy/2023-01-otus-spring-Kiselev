package ru.otus.integration.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.channel.PublishSubscribeChannel;
import org.springframework.integration.channel.QueueChannel;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.MessageChannels;
import org.springframework.integration.dsl.Pollers;
import org.springframework.integration.handler.LoggingHandler;
import org.springframework.integration.scheduling.PollerMetadata;
import ru.otus.integration.models.Food;
import ru.otus.integration.models.Order;
import ru.otus.integration.models.ProductType;
import ru.otus.integration.services.bar.BarService;
import ru.otus.integration.services.food.KitchenService;

@Configuration
public class IntegrationConfig {

    @Bean(name = PollerMetadata.DEFAULT_POLLER)
    public PollerMetadata poller() {
        return Pollers
                .fixedRate(1000)
                .get();
    }


    @Bean
    public QueueChannel drinkChannel() {
        return MessageChannels
                .queue(100)
                .datatype(Order.class)
                .get();
    }

    @Bean
    public QueueChannel foodChannel() {
        return MessageChannels
                .queue(100)
                .datatype(Order.class)
                .get();
    }

    @Bean
    public QueueChannel readyFoodChannel() {
        return MessageChannels
                .queue(100)
                .datatype(Food.class)
                .get();
    }

    @Bean
    public PublishSubscribeChannel mergeFoodChannel() {
        return MessageChannels
                .publishSubscribe()
                .get();
    }

    @Bean
    public QueueChannel orderChannel() {
        return MessageChannels
                .queue(100)
                .get();
    }


    @Bean
    public IntegrationFlow cafeFlow() {
        return IntegrationFlows
                .from(orderChannel())
                .log(LoggingHandler.Level.DEBUG)
                .split()
                .<Order, ProductType>route(
                        Order::getProductType,
                        mapping -> mapping
                                .subFlowMapping(ProductType.DRINK, sf -> sf.channel(drinkChannel()))
                                .subFlowMapping(ProductType.FOOD, sf -> sf.channel(foodChannel()))
                )
                .get();
    }

    @Bean
    public IntegrationFlow barFlow(BarService barService) {
        return IntegrationFlows
                .from(drinkChannel())
                .handle(barService, "pourDrink")
                .channel(readyFoodChannel())
                .get();
    }

    @Bean
    public IntegrationFlow kitchenFlow(KitchenService kitchenService) {
        return IntegrationFlows
                .from(foodChannel())
                .handle(kitchenService, "prepareFood")
                .channel(readyFoodChannel())
                .get();
    }

    @Bean
    public IntegrationFlow readyFood() {
        return IntegrationFlows
                .from(readyFoodChannel())
                .aggregate()
                .channel(mergeFoodChannel())
                .get();
    }


}
