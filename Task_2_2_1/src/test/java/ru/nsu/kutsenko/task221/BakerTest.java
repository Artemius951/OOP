package ru.nsu.kutsenko.task221;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

class BakerTest {

    private static class TestOrderQueue extends OrderQueue {
        private final List<Order> orders = new ArrayList<>();

        TestOrderQueue(List<Order> initial) {
            orders.addAll(initial);
        }

        @Override
        public synchronized Order take() {
            if (orders.isEmpty()) {
                return null;
            }
            return orders.remove(0);
        }
    }

    private static class TestWarehouse extends Warehouse {
        private final List<Order> stored = new ArrayList<>();

        TestWarehouse() {
            super(100);
        }

        @Override
        public synchronized void put(Order order) {
            stored.add(order);
        }

        List<Order> getStored() {
            return stored;
        }
    }

    private static class TestLogger implements OrderLogger {
        private final List<OrderStatus> statuses = new ArrayList<>();

        @Override
        public void logOrderState(Order order, OrderStatus status) {
            statuses.add(status);
        }

        List<OrderStatus> getStatuses() {
            return statuses;
        }
    }

    @Test
    void bakerProcessesSingleOrder() throws InterruptedException {
        Order order = new Order(1);
        List<Order> initial = new ArrayList<>();
        initial.add(order);

        TestOrderQueue queue = new TestOrderQueue(initial);
        TestWarehouse warehouse = new TestWarehouse();
        TestLogger logger = new TestLogger();

        Baker baker = new Baker(3, 1L, queue, warehouse, logger);
        Thread thread = new Thread(baker);
        thread.start();
        thread.join();

        assertEquals(OrderStatus.READY, order.getStatus());
        assertEquals(Integer.valueOf(3), order.getBakerId());

        assertEquals(1, warehouse.getStored().size());
        assertTrue(warehouse.getStored().contains(order));

        List<OrderStatus> statuses = logger.getStatuses();
        assertTrue(statuses.contains(OrderStatus.COOKING));
        assertTrue(statuses.contains(OrderStatus.READY));
    }

    @Test
    void bakerStopsWhenQueueReturnsNull() throws InterruptedException {
        List<Order> empty = new ArrayList<>();
        TestOrderQueue queue = new TestOrderQueue(empty);
        TestWarehouse warehouse = new TestWarehouse();
        TestLogger logger = new TestLogger();

        Baker baker = new Baker(1, 1L, queue, warehouse, logger);
        Thread thread = new Thread(baker);
        thread.start();
        thread.join();

        assertTrue(warehouse.getStored().isEmpty());
        assertTrue(logger.getStatuses().isEmpty());
    }
}