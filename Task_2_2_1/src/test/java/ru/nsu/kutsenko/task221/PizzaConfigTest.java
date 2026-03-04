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
        setField(config, "bakersCount", 3);
        setField(config, "bakerCookingTimeMillis", 200L);
        setField(config, "couriersCount", 2);
        setField(config, "courierCapacity", 5);
        setField(config, "courierDeliveryTimeMillis", 500L);

        assertEquals(10, config.getWarehouseCapacity());
        assertEquals(1000L, config.getWorkTimeMillis());
        assertEquals(3, config.getBakersCount());
        assertEquals(200L, config.getBakerCookingTimeMillis());
        assertEquals(2, config.getCouriersCount());
        assertEquals(5, config.getCourierCapacity());
        assertEquals(500L, config.getCourierDeliveryTimeMillis());
    }

    private void setField(PizzaConfig config, String name, Object value) throws Exception {
        Field field = PizzaConfig.class.getDeclaredField(name);
        field.setAccessible(true);
        field.set(config, value);
    }
}