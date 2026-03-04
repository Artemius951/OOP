package ru.nsu.kutsenko.task221;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class OrderQueueTest {

    @Test
    void putAndTakeReturnSameOrder() throws InterruptedException {
        OrderQueue queue = new OrderQueue();
        Order order = new Order(1);

        queue.put(order);
        assertFalse(queue.isEmpty());

        Order taken = queue.take();
        assertEquals(order, taken);
        assertTrue(queue.isEmpty());
    }

    @Test
    void fifoOrderIsPreserved() throws InterruptedException {
        OrderQueue queue = new OrderQueue();
        Order first = new Order(1);
        Order second = new Order(2);

        queue.put(first);
        queue.put(second);

        Order taken1 = queue.take();
        Order taken2 = queue.take();

        assertEquals(first, taken1);
        assertEquals(second, taken2);
    }

    @Test
    void stopAcceptingPreventsNewOrders() {
        OrderQueue queue = new OrderQueue();
        queue.stopAccepting();

        Order order = new Order(1);
        assertFalse(queue.isAccepting());
        assertThrows(IllegalStateException.class, () -> queue.put(order));
    }

    @Test
    void takeReturnsNullWhenClosedAndEmpty() throws InterruptedException {
        OrderQueue queue = new OrderQueue();
        queue.stopAccepting();

        assertTrue(queue.isEmpty());
        assertFalse(queue.isAccepting());

        Order taken = queue.take();
        assertNull(taken);
    }
}