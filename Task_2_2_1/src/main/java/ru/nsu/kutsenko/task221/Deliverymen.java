package ru.nsu.kutsenko.task221;

import java.util.List;

/**
 * Курьер, доставляющий готовые заказы.
 * Забирает заказы из хранилища и доставляет их клиентам.
 */
public class Deliverymen implements Runnable {

    private final int id;
    private final int capacity;
    private final Warehouse warehouse;
    private final OrderLogger logger;
    private final long deliveryTimeMillis;

    /**
     * Создает курьера с заданными параметрами.
     *
     * @param id идентификатор курьера.
     * @param capacity максимальное количество заказов за одну поездку.
     * @param warehouse хранилище готовых заказов.
     * @param logger логгер для записи состояний заказов.
     * @param deliveryTimeMillis время доставки в миллисекундах.
     */
    public Deliverymen(int id,
                       int capacity,
                       Warehouse warehouse,
                       OrderLogger logger,
                       long deliveryTimeMillis) {
        this.id = id;
        this.capacity = capacity;
        this.warehouse = warehouse;
        this.logger = logger;
        this.deliveryTimeMillis = deliveryTimeMillis;
    }

    /**
     * Основной цикл работы курьера.
     * Забирает заказы из хранилища, доставляет их и отмечает как доставленные.
     * Завершает работу, когда хранилище закрыто и пусто.
     */
    @Override
    public void run() {
        try {
            while (true) {
                List<Order> orders = warehouse.take(capacity);

                if (orders == null) {
                    break;
                }

                if (orders.isEmpty()) {
                    continue;
                }

                for (Order order : orders) {
                    order.setCourierId(id);
                    order.setStatus(OrderStatus.DELIVERING);
                    logger.logOrderState(order, OrderStatus.DELIVERING);
                }

                Thread.sleep(deliveryTimeMillis);

                for (Order order : orders) {
                    order.setStatus(OrderStatus.DELIVERED);
                    logger.logOrderState(order, OrderStatus.DELIVERED);
                }
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}