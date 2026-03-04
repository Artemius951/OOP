package ru.nsu.kutsenko.task221;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

class WarehouseTest {

    @Test
    void putAndTakeRespectCapacityAndOrder() throws InterruptedException {
        Warehouse warehouse = new Warehouse(2);
        Order first = new Order(1);
        Order second = new Order(2);

        warehouse.put(first);
        warehouse.put(second);

        assertTrue(warehouse.isFull());
        assertFalse(warehouse.isEmpty());

        List<Order> taken = warehouse.take(2);

        assertEquals(2, taken.size());
        assertEquals(first, taken.get(0));
        assertEquals(second, taken.get(1));

        assertTrue(warehouse.isEmpty());
        assertFalse(warehouse.isFull());
    }

    @Test
    void takeReturnsAtMostRequestedCount() throws InterruptedException {
        Warehouse warehouse = new Warehouse(5);
        Order o1 = new Order(1);
        Order o2 = new Order(2);
        Order o3 = new Order(3);

        warehouse.put(o1);
        warehouse.put(o2);
        warehouse.put(o3);

        List<Order> taken = warehouse.take(2);

        assertEquals(2, taken.size());
        assertEquals(o1, taken.get(0));
        assertEquals(o2, taken.get(1));
        assertFalse(warehouse.isEmpty());
    }

    @Test
    void stopAcceptingPreventsNewOrders() throws InterruptedException {
        Warehouse warehouse = new Warehouse(1);
        warehouse.stopAccepting();

        assertFalse(warehouse.isAccepting());
        Order order = new Order(1);

        assertThrows(IllegalStateException.class, () -> warehouse.put(order));
    }

    @Test
    void takeReturnsNullWhenClosedAndEmpty() throws InterruptedException {
        Warehouse warehouse = new Warehouse(1);
        warehouse.stopAccepting();

        assertFalse(warehouse.isAccepting());
        assertTrue(warehouse.isEmpty());

        List<Order> taken = warehouse.take(3);
        assertNull(taken);
    }
}