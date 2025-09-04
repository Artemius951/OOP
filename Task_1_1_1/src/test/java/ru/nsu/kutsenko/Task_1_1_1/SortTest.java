package ru.nsu.kutsenko.Task_1_1_1;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class SortTest {

    @Test
    public void sort() {
        testSimpleSort();
        testReverse();
        testNegativeAndZero();
        testOneElement();
        testMixedSigns();
        testLargeNumbers();
        testLargeNegativeNumbers();
        testRepeatedNumbers();
        testSameElements();
        testMaxMinValues();
    }

    private void testSimpleSort() {
        int[] array = new int[]{1, 3, 2};
        Sort.heapSort(array);
        assertArrayEquals(new int[]{1, 2, 3}, array);
    }

    private void testReverse() {
        int[] array = new int[]{5, 4, 3, 2, 1};
        Sort.heapSort(array);
        assertArrayEquals(new int[]{1, 2, 3, 4, 5}, array);
    }

    private void testNegativeAndZero() {
        int[] array = new int[]{-1, -2, -3, -4, 0};
        Sort.heapSort(array);
        assertArrayEquals(new int[]{-4, -3, -2, -1, 0}, array);
    }

    private void testOneElement() {
        int[] array = new int[]{951};
        Sort.heapSort(array);
        assertArrayEquals(new int[]{951}, array);
    }

    private void testMixedSigns() {
        int[] array = new int[]{3, 2, 1, 0, -1, -2, -3};
        Sort.heapSort(array);
        assertArrayEquals(new int[]{-3, -2, -1, 0, 1, 2, 3}, array);
    }

    private void testLargeNumbers() {
        int[] array = new int[]{214748364, 1000000000, 500000000, 999999999, 1234567890};
        Sort.heapSort(array);
        assertArrayEquals(new int[]{214748364, 500000000, 999999999, 1000000000, 1234567890}, array);
    }

    private void testLargeNegativeNumbers() {
        int[] array = new int[]{-214748364, -1000000000, -500000000, -999999999, -1234567890};
        Sort.heapSort(array);
        assertArrayEquals(new int[]{-1234567890, -1000000000, -999999999, -500000000, -214748364}, array);
    }

    private void testRepeatedNumbers() {
        int[] array = new int[]{8, 800, 88, 888, 8, 88, 888, 800};
        Sort.heapSort(array);
        assertArrayEquals(new int[]{8, 8, 88, 88, 800, 800, 888, 888}, array);
    }

    private void testSameElements() {
        int[] array = new int[]{9, 9, 9, 9, 9};
        Sort.heapSort(array);
        assertArrayEquals(new int[]{9, 9, 9, 9, 9}, array);
    }

    private void testMaxMinValues() {
        int[] array = new int[]{Integer.MAX_VALUE, 0, Integer.MIN_VALUE};
        Sort.heapSort(array);
        assertArrayEquals(new int[]{Integer.MIN_VALUE, 0, Integer.MAX_VALUE}, array);
    }
}