package ru.nsu.kutsenko.task211;

/**
 * Класс для проверки чисел на простоту.
 * Содержит метод, определяющий, является ли число простым.
 */
public class Primality {

    /**
     * Проверяет, является ли переданное число простым.
     * Числа меньше или равные 1 непростые.
     * Чётные числа, кроме 2, сразу отбрасываются.
     * Делители проверяются до квадратного корня из числа.
     *
     * @param n целое число для проверки.
     * @return true, если число простое, иначе false.
     */
    public static boolean isPrime(int n) {
        if (n <= 1) {
            return false;
        }
        if (n == 2) {
            return true;
        }
        if (n % 2 == 0) {
            return false;
        }

        for (int i = 3; i*i <=n; i +=2) {
            if (n%i == 0) {
                return false;
            }
        }
        return true;
    }
}
