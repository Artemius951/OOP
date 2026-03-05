package ru.nsu.kutsenko.task221;

/**
 * Класс, представляющий заказ пиццы.
 * Содержит всю информацию о жизненном цикле конкретного заказа.
 */
public class Order {

    private final int id;
    private final long createdAtMillis;

    private OrderStatus status;

    private Integer bakerId;
    private Integer courierId;

    public Order(int id) {
        this.id = id;
        this.createdAtMillis = System.currentTimeMillis();
        this.status = OrderStatus.NEW;
    }

    public int getId() {
        return id;
    }

    public long getCreatedAtMillis() {
        return createdAtMillis;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public Integer getBakerId() {
        return bakerId;
    }

    public void setBakerId(Integer bakerId) {
        this.bakerId = bakerId;
    }

    public Integer getCourierId() {
        return courierId;
    }

    public void setCourierId(Integer courierId) {
        this.courierId = courierId;
    }
}