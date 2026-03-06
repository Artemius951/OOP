package ru.nsu.kutsenko.task221;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.lang.reflect.Field;
import org.junit.jupiter.api.Test;

class PizzaConfigTest {

    @Test
    void gettersReturnUnderlyingFieldValues() throws Exception {
        PizzaConfig config = new PizzaConfig();

        setField(config, "warehouseCapacity", 10);
        setField(config, "workTimeMillis", 1000L);

        setField(config, "bakerCookingTimeMillis", new long[]{200L, 300L});
        setField(config, "courierCapacity", new int[]{5, 7});
        setField(config, "courierDeliveryTimeMillis", 500L);

        assertEquals(10, config.getWarehouseCapacity());
        assertEquals(1000L, config.getWorkTimeMillis());

        // пекари
        assertEquals(2, config.getBakersCount());
        assertEquals(200L, config.getBakerCookingTimeMillis(0));
        assertEquals(300L, config.getBakerCookingTimeMillis(1));

        // курьеры
        assertEquals(2, config.getCouriersCount());
        assertEquals(5, config.getCourierCapacity(0));
        assertEquals(7, config.getCourierCapacity(1));

        assertEquals(500L, config.getCourierDeliveryTimeMillis());
    }

    private void setField(PizzaConfig config, String name, Object value) throws Exception {
        Field field = PizzaConfig.class.getDeclaredField(name);
        field.setAccessible(true);
        field.set(config, value);
    }
}