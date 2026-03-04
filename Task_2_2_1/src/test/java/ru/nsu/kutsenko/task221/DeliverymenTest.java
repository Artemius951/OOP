package ru.nsu.kutsenko.task221;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Test;

class DeliverymenTest {

    private static class TestWarehouse extends Warehouse {
        private final List<List<Order>> batches = new ArrayList<>();

        TestWarehouse(List<List<Order>> batches) {
            super(100);
            this.batches.addAll(batches);
        }

        @Override
        public synchronized List<Order> take(int maxCount) {
            if (batches.isEmpty()) {
                return null;
            }
            return batches.remove(0);
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
    void courierDeliversBatchOfOrders() throws InterruptedException {
        Order o1 = new Order(1);
        Order o2 = new Order(2);
        List<Order> batch = new ArrayList<>();
        batch.add(o1);
        batch.add(o2);

        List<List<Order>> batches = new ArrayList<>();
        batches.add(batch);
        batches.add(Collections.emptyList());

        TestWarehouse warehouse = new TestWarehouse(batches);
        TestLogger logger = new TestLogger();

        Deliverymen courier = new Deliverymen(7, 2, warehouse, logger,
            1L);
        Thread thread = new Thread(courier);
        thread.start();
        thread.join();

        assertEquals(OrderStatus.DELIVERED, o1.getStatus());
        assertEquals(OrderStatus.DELIVERED, o2.getStatus());
        assertEquals(Integer.valueOf(7), o1.getCourierId());
        assertEquals(Integer.valueOf(7), o2.getCourierId());

        List<OrderStatus> statuses = logger.getStatuses();
        assertTrue(statuses.contains(OrderStatus.DELIVERING));
        assertTrue(statuses.contains(OrderStatus.DELIVERED));
    }

    @Test
    void courierStopsWhenWarehouseReturnsNull() throws InterruptedException {
        List<List<Order>> batches = new ArrayList<>();
        TestWarehouse warehouse = new TestWarehouse(batches);
        TestLogger logger = new TestLogger();

        Deliverymen courier = new Deliverymen(1, 1, warehouse, logger,
            1L);
        Thread thread = new Thread(courier);
        thread.start();
        thread.join();

        assertTrue(logger.getStatuses().isEmpty());
    }
}