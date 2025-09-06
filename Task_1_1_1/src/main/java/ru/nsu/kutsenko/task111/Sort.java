package ru.nsu.kutsenko.task111;


/**
 * Класс для сортировки массива с использованием алгоритма Heap Sort (сортировки кучей).
 */
public class Sort {
    /**
     * Проверяет массив на null или пустоту и выводит соответствующее сообщение.
     *
     * @param arr массив для проверки
     * @return true если массив нормальный, false в противном случае
     */
    private static boolean check(int[] arr) {
        if (arr == null) {
            System.out.println("null");
            return false;
        }
        if (arr.length == 0) {
            System.out.println("пустой массив");
            return false;
        }
        return true;
    }

    /**
     * Восстанавливает свойства кучи для поддерева с корнем в заданном индексе.
     *
     * @param arr массив, представляющий бинарное дерево
     * @param length размер кучи (количество элементов)
     * @param index индекс корня поддерева для восстановления свойств кучи
     */
    private static void sift(int[] arr, int length, int index) {
        int maxId = index;
        int leftson = 2 * index + 1;
        int rightson = 2 * index + 2;

        if (leftson < length && arr[leftson] > arr[maxId]) {
            maxId = leftson;
        }
        if (rightson < length && arr[rightson] > arr[maxId]) {
            maxId = rightson;
        }
        if (maxId != index) {
            int swap = arr[index];
            arr[index] = arr[maxId];
            arr[maxId] = swap;

            sift(arr, length, maxId);
        }
    }

    /**
     * Сортирует массив по возрастанию с использованием алгоритма сортировки кучей.
     * Если массив null или пустой, выводит соответствующее сообщение и завершает работу.
     *
     * @param arr массив для сортировки
     */
    public static void heapSort(int[] arr) {
        if (!check(arr)) {
            return;
        }

        int length = arr.length;
        for (int index = length / 2 - 1; index >= 0; index--) {
            sift(arr, length, index);
        }
        for (int index = length - 1; index > 0; index--) {
            int swap = arr[0];
            arr[0] = arr[index];
            arr[index] = swap;

            sift(arr, index, 0);
        }
    }
    /**
     * Простой main, который ничего не делает, но нужен для создания манифеста
     * 
     * @param args просто пустышка
     */
    public static void main(String[] args) {
    }
}