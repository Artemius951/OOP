package ru.nsu.kutsenko.task211;

/**
 * Параллельная проверка массива с ручным управлением потоками.
 * Разбивает массив на блоки и распределяет между указанным числом потоков.
 * Каждый поток проверяет свой блок, при нахождении непростого числа завершает работу.
 */
public class ParallPrimeCheckThread {

    /**
     * Выполняет параллельную проверку массива на наличие непростых чисел.
     *
     * @param numbers массив целых чисел для проверки.
     * @param threadCount количество потоков.
     * @return true, если найдено непростое число, иначе false.
     * @throws InterruptedException если прервано ожидание завершения потоков.
     */
    public static boolean withoutPrime(int[] numbers, int threadCount) throws InterruptedException {
        if (threadCount < 1) {
            throw new IllegalArgumentException("Thread count must be at least 1");
        }

        int n = numbers.length;
        if (n == 0) {
            return false;
        }

        Thread[] threads = new Thread[threadCount];
        boolean[] threadFoundNonPrime = new boolean[threadCount];

        int chunkSize = (n + threadCount - 1) / threadCount;

        for (int t = 0; t < threadCount; t++) {
            int threadIndex = t;
            int start = t * chunkSize;
            int end = Math.min(start + chunkSize, n);

            if (start >= end) {
                threads[t] = null;
                continue;
            }

            threads[t] = new Thread(() -> {
                for (int i = start; i < end; i++) {
                    if (!Primality.isPrime(numbers[i])) {
                        threadFoundNonPrime[threadIndex] = true;
                        break;
                    }
                }
            });

            threads[t].start();
        }

        for (Thread thread : threads) {
            if (thread != null) {
                thread.join();
            }
        }

        for (boolean found : threadFoundNonPrime) {
            if (found) {
                return true;
            }
        }
        return false;
    }
}