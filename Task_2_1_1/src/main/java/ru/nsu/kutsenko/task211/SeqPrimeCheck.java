package ru.nsu.kutsenko.task211;

/**
 * Последовательная проверка массива чисел на наличие непростых чисел.
 * Однопоточный перебор элементов с ранним выходом.
 */
public class SeqPrimeCheck {

    /**
     * Выполняет последовательную проверку элементов массива.
     * При обнаружении первого непростого числа метод завершает работу.
     * Для пустого массива возвращает false.
     *
     * @return true, если в массиве есть хотя бы одно непростое число,
     *         false если все числа простые или массив пуст.
     */
    public static boolean withoutPrime(int[] numb) {
        for (int n : numb) {
            if (!Primality.isPrime(n)) {
                return true;
            }
        }
        return false;
    }
}
