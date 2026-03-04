package ru.nsu.kutsenko.task221;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;


import org.junit.jupiter.api.Test;

class OrderTest {

    @Test
    void constructorInitializesFields() {
        Order order = new Order(42);

        assertEquals(42, order.getId());
        assertNotNull(order.getCreatedAtMillis());
        assertEquals(OrderStatus.NEW, order.getStatus());
        assertNull(order.getBakerId());
        assertNull(order.getCourierId());
    }

    @Test
    void settersUpdateStatusAndIds() {
        Order order = new Order(1);

        order.setStatus(OrderStatus.COOKING);
        assertEquals(OrderStatus.COOKING, order.getStatus());

        order.setBakerId(10);
        assertEquals(Integer.valueOf(10), order.getBakerId());

        order.setCourierId(20);
        assertEquals(Integer.valueOf(20), order.getCourierId());
    }
}