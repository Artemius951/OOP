package ru.nsu.kutsenko.Task_1_1_1;

public class Sort {
    public static int[] check(int[] arr) {
        assert arr != null : "Массив не должен быть null";
        assert arr.length > 0 : "Размер массива должен быть больше 0";
        return arr;
    }

    private static void sift(int[] arr, int length, int i) {
        int maxID = i;
        int leftson = 2 * i + 1;
        int rightson = 2 * i + 2;

        if (leftson < length && arr[leftson] > arr[maxID]) {
            maxID = leftson;
        }
        if (rightson < length && arr[rightson] > arr[maxID]) {
            maxID = rightson;
        }
        if (maxID != i) {
            int swap = arr[i];
            arr[i] = arr[maxID];
            arr[maxID] = swap;

            sift(arr,length, maxID);
        }
    }


    public static int[] heapSort(int[] arr) {
        check(arr);
        int length = arr.length;
        for (int i = length/2-1; i >= 0; i--) {
            sift(arr,length,i);
        }
        for (int i = length - 1; i > 0; i--) {
            int swap = arr[0];
            arr[0] = arr[i];
            arr[i] = swap;

            sift(arr,i,0);
        }

        return arr;
    }
}