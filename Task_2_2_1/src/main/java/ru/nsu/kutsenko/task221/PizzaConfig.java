package ru.nsu.kutsenko.task221;

/**
 * Конфигурационный класс пиццерии.
 * Содержит все настраиваемые параметры для работы пиццерии.
 */
public class PizzaConfig {

    private int warehouseCapacity;
    private long workTimeMillis;

    private int bakersCount;
    private long bakerCookingTimeMillis;

    private int couriersCount;
    private int courierCapacity;
    private long courierDeliveryTimeMillis;

    /**
     * Возвращает максимальную вместимость хранилища готовых заказов.
     *
     * @return вместимость хранилища.
     */
    public int getWarehouseCapacity() {
        return warehouseCapacity;
    }

    /**
     * Возвращает время работы пиццерии в миллисекундах.
     *
     * @return время работы.
     */
    public long getWorkTimeMillis() {
        return workTimeMillis;
    }

    /**
     * Возвращает количество пекарей.
     *
     * @return количество пекарей.
     */
    public int getBakersCount() {
        return bakersCount;
    }

    /**
     * Возвращает время приготовления одной пиццы в миллисекундах.
     *
     * @return время приготовления.
     */
    public long getBakerCookingTimeMillis() {
        return bakerCookingTimeMillis;
    }

    /**
     * Возвращает количество курьеров.
     *
     * @return количество курьеров.
     */
    public int getCouriersCount() {
        return couriersCount;
    }

    /**
     * Возвращает вместимость курьера (сколько заказов может взять за раз).
     *
     * @return вместимость курьера.
     */
    public int getCourierCapacity() {
        return courierCapacity;
    }

    /**
     * Возвращает время доставки в миллисекундах.
     *
     * @return время доставки.
     */
    public long getCourierDeliveryTimeMillis() {
        return courierDeliveryTimeMillis;
    }
}