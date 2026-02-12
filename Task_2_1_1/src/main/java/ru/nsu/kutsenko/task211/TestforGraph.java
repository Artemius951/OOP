package ru.nsu.kutsenko.task211;

/**
 * Тестовый класс для сравнения производительности трех реализаций.
 * Использует большой массив повторяющихся простых чисел размером 5 млн элементов.
 * Замеряет время выполнения в миллисекундах для каждого подхода.
 */
public class TestforGraph {

    /**
     * Создает тестовые данные, последовательно запускает все три реализации
     * Выводит результаты замеров в консоль.
     *
     * @param args аргументы командной строки (не используются).
     * @throws InterruptedException при ошибках ожидания потоков.
     */
    public static void main(String[] args) throws InterruptedException {
        int size = 5_000_000;
        int[] data = new int[size];
        int[] bigPrimes = {
            1_000_000_007,
            1_000_000_009,
            1_000_000_021,
            1_000_000_033
        };

        for (int i = 0; i < size; i++) {
            data[i] = bigPrimes[i % bigPrimes.length];
        }

        long start = System.nanoTime();
        boolean seq = new SeqPrimeCheck(data).withoutPrime();
        long end = System.nanoTime();
        long seqMs = (end - start) / 1_000_000;
        System.out.println("Seq: " + seqMs + " ms, result = " + seq);

        int threads = 8;
        start = System.nanoTime();
        boolean par = new ParallPrimeCheckThread(data, threads).withoutPrime();
        end = System.nanoTime();
        long parMs = (end - start) / 1_000_000;
        System.out.println("Thread (" + threads + "): " + parMs + " ms, result = " + par);

        start = System.nanoTime();
        boolean stream = ParallPrimeCheckStream.withoutPrime(data);
        end = System.nanoTime();
        long streamMs = (end - start) / 1_000_000;
        System.out.println("parallelStream: " + streamMs + " ms, result = " + stream);
    }
}