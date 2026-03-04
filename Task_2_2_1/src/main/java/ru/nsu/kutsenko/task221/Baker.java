package ru.nsu.kutsenko.task221;

/**
 * Пекарь, обрабатывающий заказы.
 * Забирает заказы из очереди, готовит их и помещает в хранилище.
 */
public class Baker implements Runnable {

    private final int id;
    private final long cookingTimePerPizzaMillis;
    private final OrderQueue orderQueue;
    private final Warehouse warehouse;
    private final OrderLogger logger;

    /**
     * Создает пекаря с заданными параметрами.
     *
     * @param id идентификатор пекаря.
     * @param cookingTimePerPizzaMillis время приготовления одной пиццы.
     * @param orderQueue очередь заказов.
     * @param warehouse хранилище готовых заказов.
     * @param logger логгер для записи состояний заказов.
     */
    public Baker(int id,
                 long cookingTimePerPizzaMillis,
                 OrderQueue orderQueue,
                 Warehouse warehouse,
                 OrderLogger logger) {
        this.id = id;
        this.cookingTimePerPizzaMillis = cookingTimePerPizzaMillis;
        this.orderQueue = orderQueue;
        this.warehouse = warehouse;
        this.logger = logger;
    }

    /**
     * Основной цикл работы пекаря.
     * Последовательно забирает заказы из очереди, готовит их и передает в хранилище.
     * Завершает работу, когда очередь закрыта и пуста.
     */
    @Override
    public void run() {
        try {
            while (true) {
                Order order = orderQueue.take();
                if (order == null) {
                    break;
                }

                order.setBakerId(id);
                order.setStatus(OrderStatus.COOKING);
                logger.logOrderState(order, OrderStatus.COOKING);

                Thread.sleep(cookingTimePerPizzaMillis);

                order.setStatus(OrderStatus.READY);
                logger.logOrderState(order, OrderStatus.READY);

                warehouse.put(order);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}