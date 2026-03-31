package ru.nsu.kutsenko.task221;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class OrderStatusTest {

    @Test
    void enumHasExpectedValuesInOrder() {
        OrderStatus[] values = OrderStatus.values();

        assertEquals(6, values.length);
        assertEquals(OrderStatus.NEW, values[0]);
        assertEquals(OrderStatus.COOKING, values[1]);
        assertEquals(OrderStatus.READY, values[2]);
        assertEquals(OrderStatus.DELIVERING, values[3]);
        assertEquals(OrderStatus.DELIVERED, values[4]);
        assertEquals(OrderStatus.CANCELLED, values[5]);
    }
}