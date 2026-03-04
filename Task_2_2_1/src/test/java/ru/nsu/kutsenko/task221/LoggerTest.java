package ru.nsu.kutsenko.task221;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.jupiter.api.Test;

class LoggerTest {

    @Test
    void logOrderStatePrintsOrderIdAndStatus() {
        Logger logger = new Logger();
        Order order = new Order(7);

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream original = System.out;
        System.setOut(new PrintStream(out));
        try {
            logger.logOrderState(order, OrderStatus.READY);
        } finally {
            System.setOut(original);
        }

        String printed = out.toString();
        assertTrue(printed.contains("[orderId=7]"));
        assertTrue(printed.contains("[READY]"));
    }
}