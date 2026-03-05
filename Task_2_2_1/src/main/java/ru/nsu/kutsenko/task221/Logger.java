package ru.nsu.kutsenko.task221;

/**
 * Реализация логгера состояний заказов.
 * Выводит информацию о заказах в стандартный поток вывода (stdout).
 */
public class Logger implements OrderLogger {

    /**
     * Выводит состояние заказа в формате: [orderId=N] [СТАТУС].
     *
     * @param order заказ, состояние которого изменилось.
     */
    @Override
    public void logOrderState(Order order) {
        System.out.printf("[orderId=%d] [%s]%n", order.getId(), order.getStatus());
    }
}