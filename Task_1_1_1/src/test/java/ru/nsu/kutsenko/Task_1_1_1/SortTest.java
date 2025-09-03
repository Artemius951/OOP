package ru.nsu.kutsenko.Task_1_1_1;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SortTest {

    @Test
    public void sort() {
        int[] array = new int[]{1, 3, 2};
        int[] result = Sort.heapSort(array);
        assertArrayEquals(new int[]{1, 2, 3}, result);

        array = new int[]{-1, -2, -3, -4, 0};
        result = Sort.heapSort(array);
        assertArrayEquals(new int[]{-4, -3, -2, -1, 0}, result);

        array = new int[]{951};
        result = Sort.heapSort(array);
        assertArrayEquals(new int[]{951},result);

        array = new int[]{3, 2, 1, 0, -1, -2, -3};
        result = Sort.heapSort(array);
        assertArrayEquals(new int[]{-3, -2, -1, 0, 1, 2, 3}, result);

        array = new int[]{100, 101, 102, 951};
        result = Sort.heapSort(array);
        assertArrayEquals(new int[]{100, 101, 102, 951}, result);

        array = new int[]{8, 800, 88, 888, 8, 88, 888, 800};
        result = Sort.heapSort(array);
        assertArrayEquals(new int[]{8, 8, 88, 88, 800, 800, 888, 888}, result);

        array = new int[]{9, 9, 9, 9, 9};
        result = Sort.heapSort(array);
        assertArrayEquals(new int[]{9, 9, 9, 9, 9}, result);

        array = new int[]{Integer.MAX_VALUE, 0, Integer.MIN_VALUE};
        result = Sort.heapSort(array);
        assertArrayEquals(new int[]{Integer.MIN_VALUE, 0, Integer.MAX_VALUE}, result);
    }
}

