package ru.nsu.kutsenko.task211;

/**
 * Последовательная проверка массива чисел на наличие непростых чисел.
 * Однопоточный перебор элементов с ранним выходом.
 */
public class SeqPrimeCheck {

    private final int[] numb;

    /**
     * Создает объект для проверки переданного массива.
     *
     * @param numb массив целых чисел для анализа.
     */
    public SeqPrimeCheck(int[] numb) {

        this.numb = numb;
    }

    /**
     * Выполняет последовательную проверку элементов массива.
     * При обнаружении первого непростого числа метод завершает работу.
     * Для пустого массива возвращает false.
     *
     * @return true, если в массиве есть хотя бы одно непростое число,
     *         false если все числа простые или массив пуст.
     */
    public boolean withoutPrime() {
        for (int n : numb) {
            if (!Primality.isPrime(n)) {
                return true;
            }
        }
        return false;
    }
}
