package ru.nsu.kutsenko.Task_1_1_1;

public class Sort {
    private static void check(int[] arr) {
        assert arr != null : "Массив не должен быть null";
        assert arr.length > 0 : "Размер массива должен быть больше 0";
    }

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

            sift(arr,length, maxId);
        }
    }


    public static void heapSort(int[] arr) {
        check(arr);
        int length = arr.length;
        for (int i = length / 2 - 1; i >= 0; i--) {
            sift(arr, length, i);
        }
        for (int i = length - 1; i > 0; i--) {
            int swap = arr[0];
            arr[0] = arr[i];
            arr[i] = swap;

            sift(arr, i, 0);
        }
    }
}