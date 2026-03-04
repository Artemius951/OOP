package ru.nsu.kutsenko.task221;

/**
 * Реализация логгера состояний заказов.
 * Выводит информацию о заказах в стандартный поток вывода (stdout).
 */
public class Logger implements OrderLogger {

    /**
     * Выводит состояние заказа в формате: [orderId=N] [СТАТУС]
     *
     * @param order заказ, состояние которого изменилось.
     * @param status новое состояние заказа.
     */
    @Override
    public void logOrderState(Order order, OrderStatus status) {
        System.out.printf("[orderId=%d] [%s]%n", order.getId(), status);
    }
}