package ru.nsu.kutsenko.task221;

import com.google.gson.Gson;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

/**
 * Главный класс пиццерии.
 * Загружает конфигурацию, создает и запускает все компоненты системы.
 */
public class Pizzeria {

    /**
     * Точка входа.
     * Загружает конфигурацию, создает пекарей и курьеров,
     * генерирует заказы в течение заданного времени.
     *
     * @param args аргументы командной строки.
     */
    public static void main(String[] args) {

        String configPath = (args != null && args.length > 0)
            ? args[0]
            : "config.json";

        PizzaConfig config = loadConfig(configPath);

        Logger logger = new Logger();
        OrderQueue orderQueue = new OrderQueue();
        Warehouse warehouse = new Warehouse(config.getWarehouseCapacity());

        List<Thread> bakerThreads = new ArrayList<>();
        for (int i = 0; i < config.getBakersCount(); i++) {
            int bakerId = i + 1;
            Baker baker = new Baker(
                bakerId,
                config.getBakerCookingTimeMillis(i),
                orderQueue,
                warehouse,
                logger
            );
            Thread t = new Thread(baker, "Baker-" + bakerId);
            bakerThreads.add(t);
            t.start();
        }

        List<Thread> courierThreads = new ArrayList<>();
        for (int i = 0; i < config.getCouriersCount(); i++) {
            int courierId = i + 1;
            Deliverymen courier = new Deliverymen(
                courierId,
                config.getCourierCapacity(i),
                warehouse,
                logger,
                config.getCourierDeliveryTimeMillis()
            );
            Thread t = new Thread(courier, "Courier-" + courierId);
            courierThreads.add(t);
            t.start();
        }

        long workTimeMillis = config.getWorkTimeMillis();
        long start = System.currentTimeMillis();
        int nextOrderId = 1;

        while (System.currentTimeMillis() - start < workTimeMillis) {
            Order order = new Order(nextOrderId++);
            logger.logOrderState(order);
            orderQueue.put(order);

            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }

        orderQueue.stopAccepting();

        for (Thread t : bakerThreads) {
            try {
                t.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        warehouse.stopAccepting();

        for (Thread t : courierThreads) {
            try {
                t.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    /**
     * Загружает конфигурацию из JSON-файла.
     *
     * @param path путь к файлу конфигурации.
     * @return объект конфигурации.
     * @throws RuntimeException если файл не найден или произошла ошибка чтения.
     */
    private static PizzaConfig loadConfig(String path) {
        try (Reader reader = new FileReader(path)) {
            Gson gson = new Gson();
            return gson.fromJson(reader, PizzaConfig.class);
        } catch (IOException e) {
            throw new RuntimeException("Troubles with config reading: " + path, e);
        }
    }
}