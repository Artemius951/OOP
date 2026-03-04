package ru.nsu.kutsenko.task221;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Очередь заказов для пиццерии.
 * Обеспечивает взаимодействие между генератором заказов и пекарями.
 */
public class OrderQueue {

    private final Queue<Order> queue = new LinkedList<>();
    private boolean accepting = true;

    /**
     * Добавляет новый заказ в очередь.
     *
     * @param order заказ для добавления
     * @throws IllegalStateException если очередь закрыта для новых заказов
     */
    public synchronized void put(Order order) {
        if (!accepting) {
            throw new IllegalStateException("Queue is closed for new orders");
        }
        queue.add(order);
        notifyAll();
    }

    /**
     * Извлекает заказ из очереди. Если очередь пуста и открыта.
     * Поток блокируется до появления заказа или закрытия очереди.
     *
     * @return заказ или null, если очередь закрыта и пуста
     * @throws InterruptedException если поток был прерван во время ожидания
     */
    public synchronized Order take() throws InterruptedException {

        while (queue.isEmpty() && accepting) {
            wait();
        }

        if (queue.isEmpty() && !accepting) {
            return null;
        }
        return queue.remove();
    }

    /**
     * Закрывает очередь для новых заказов.
     * Пробуждает все ожидающие потоки.
     */
    public synchronized void stopAccepting() {
        accepting = false;
        notifyAll();
    }

    /**
     * Проверяет, открыта ли очередь для новых заказов.
     *
     * @return true, если очередь принимает заказы.
     */
    public synchronized boolean isAccepting() {
        return accepting;
    }

    /**
     * Проверяет, пуста ли очередь.
     *
     * @return true, если очередь пуста.
     */
    public synchronized boolean isEmpty() {
        return queue.isEmpty();
    }
}