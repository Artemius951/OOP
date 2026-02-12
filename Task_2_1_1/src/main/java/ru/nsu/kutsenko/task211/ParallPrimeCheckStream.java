package ru.nsu.kutsenko.task211;

import java.util.Arrays;

/**
 * Параллельная проверка массива с использованием Stream API.
 */
public class ParallPrimeCheckStream {

    /**
     * Проверяет наличие непростых чисел, используя parallelStream.
     * Операция anyMatch обеспечивает раннее завершение при первом совпадении.
     *
     * @param numbers массив целых чисел для проверки.
     * @return true, если найдено хотя бы одно непростое число,
     *         false если все числа простые или массив пуст.
     */
    public static boolean withoutPrime(int[] numbers) {
        return Arrays.stream(numbers)
            .parallel()
            .anyMatch(n -> !Primality.isPrime(n));
    }
}