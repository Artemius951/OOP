package ru.nsu.kutsenko.task221;

/**
 * Интерфейс для логирования состояний заказов пиццы.
 * Позволяет отслеживать жизненный цикл каждого заказа.
 */
public interface OrderLogger {

    /**
     * Регистрирует изменение состояния заказа.
     *
     * @param order заказ, состояние которого изменилось.
     * @param status новое состояние заказа.
     */
    void logOrderState(Order order, OrderStatus status);
}