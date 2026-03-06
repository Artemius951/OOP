package ru.nsu.kutsenko.task221;

/**
 * Конфигурационный класс пиццерии.
 * Содержит все настраиваемые параметры для работы пиццерии.
 */
public class PizzaConfig {

    private int warehouseCapacity;
    private long workTimeMillis;

    private long[] bakerCookingTimeMillis;

    private int[] courierCapacity;

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
     * Равно длине массива времён приготовления.
     *
     * @return количество пекарей.
     */
    public int getBakersCount() {
        return bakerCookingTimeMillis == null ? 0 : bakerCookingTimeMillis.length;
    }

    /**
     * Возвращает время приготовления одной пиццы в миллисекундах
     * для конкретного пекаря.
     *
     * @param index индекс пекаря (от 0)
     * @return время приготовления.
     */
    public long getBakerCookingTimeMillis(int index) {
        return bakerCookingTimeMillis[index];
    }

    /**
     * Возвращает количество курьеров.
     * Равно длине массива вместимостей.
     *
     * @return количество курьеров.
     */
    public int getCouriersCount() {
        return courierCapacity == null ? 0 : courierCapacity.length;
    }

    /**
     * Возвращает вместимость курьера (сколько заказов может взять за раз)
     * для конкретного курьера.
     *
     * @param index индекс курьера (от 0)
     * @return вместимость курьера.
     */
    public int getCourierCapacity(int index) {
        return courierCapacity[index];
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