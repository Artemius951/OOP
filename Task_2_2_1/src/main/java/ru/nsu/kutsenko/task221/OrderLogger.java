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
     */
    void logOrderState(Order order);
}