package ru.nsu.kutsenko.task221;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Хранилище готовых заказов.
 * Обеспечивает взаимодействие между пекарями и курьерами.
 */
public class Warehouse {

    private final int capacity;
    private final LinkedList<Order> storage = new LinkedList<>();
    private boolean accepting = true;

    /**
     * Создает хранилище с заданной вместимостью.
     *
     * @param capacity максимальное количество заказов в хранилище.
     */
    public Warehouse(int capacity) {
        this.capacity = capacity;
    }

    /**
     * Помещает заказ в хранилище.
     * Если хранилище заполнено, поток блокируется до появления места.
     *
     * @param order заказ для размещения.
     * @throws InterruptedException если поток был прерван во время ожидания.
     * @throws IllegalStateException если хранилище закрыто для новых заказов.
     */
    public synchronized void put(Order order) throws InterruptedException {
        if (!accepting) {
            throw new IllegalStateException("Warehouse is closed for new orders");
        }
        while (storage.size() >= capacity) {
            wait();
        }
        storage.add(order);
        notifyAll();
    }

    /**
     * Забирает до maxCount заказов из хранилища.
     * Если хранилище пусто и открыто, поток блокируется до появления заказов.
     *
     * @param maxCount максимальное количество заказов для извлечения.
     * @return список заказов или null, если хранилище закрыто и пусто.
     * @throws InterruptedException если поток был прерван во время ожидания.
     */
    public synchronized List<Order> take(int maxCount) throws InterruptedException {
        while (storage.isEmpty() && accepting) {
            wait();
        }

        if (storage.isEmpty() && !accepting) {
            return null;
        }

        int count = Math.min(maxCount, storage.size());
        List<Order> result = new ArrayList<>(count);
        for (int i = 0; i < count; i++) {
            result.add(storage.removeFirst());
        }
        notifyAll();
        return result;
    }

    /**
     * Закрывает хранилище для новых заказов.
     * Пробуждает все ожидающие потоки.
     */
    public synchronized void stopAccepting() {
        accepting = false;
        notifyAll();
    }

    /**
     * Проверяет, открыто ли хранилище для новых заказов.
     *
     * @return true, если хранилище принимает заказы.
     */
    public synchronized boolean isAccepting() {
        return accepting;
    }

    /**
     * Проверяет, пусто ли хранилище.
     *
     * @return true, если хранилище пусто.
     */
    public synchronized boolean isEmpty() {
        return storage.isEmpty();
    }

    /**
     * Проверяет, заполнено ли хранилище.
     *
     * @return true, если хранилище заполнено.
     */
    public synchronized boolean isFull() {
        return storage.size() >= capacity;
    }
}