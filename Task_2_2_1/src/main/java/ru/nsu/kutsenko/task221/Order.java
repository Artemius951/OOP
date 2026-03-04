package ru.nsu.kutsenko.task221;

/**
 * Класс, представляющий заказ пиццы.
 * Содержит всю информацию о жизненном цикле конкретного заказа.
 */
public class Order {

    private final int id;
    private final long createdAtMillis;

    private volatile OrderStatus status;

    private volatile Integer bakerId;
    private volatile Integer courierId;

    /**
     * Создает новый заказ с указанным идентификатором.
     * Время создания фиксируется автоматически.
     *
     * @param id уникальный идентификатор заказа.
     */
    public Order(int id) {
        this.id = id;
        this.createdAtMillis = System.currentTimeMillis();
        this.status = OrderStatus.NEW;
    }

    /**
     * Возвращает идентификатор заказа.
     *
     * @return идентификатор заказа.
     */
    public int getId() {
        return id;
    }

    /**
     * Возвращает время создания заказа в миллисекундах.
     *
     * @return время создания.
     */
    public long getCreatedAtMillis() {
        return createdAtMillis;
    }

    /**
     * Возвращает текущий статус заказа.
     *
     * @return статус заказа.
     */
    public OrderStatus getStatus() {
        return status;
    }

    /**
     * Устанавливает новый статус заказа.
     *
     * @param status новый статус.
     */
    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    /**
     * Возвращает идентификатор пекаря, готовившего заказ.
     *
     * @return идентификатор пекаря или null.
     */
    public Integer getBakerId() {
        return bakerId;
    }

    /**
     * Устанавливает идентификатор пекаря.
     *
     * @param bakerId идентификатор пекаря.
     */
    public void setBakerId(Integer bakerId) {
        this.bakerId = bakerId;
    }

    /**
     * Возвращает идентификатор курьера, доставлявшего заказ.
     *
     * @return идентификатор курьера или null.
     */
    public Integer getCourierId() {
        return courierId;
    }

    /**
     * Устанавливает идентификатор курьера.
     *
     * @param courierId идентификатор курьера.
     */
    public void setCourierId(Integer courierId) {
        this.courierId = courierId;
    }
}