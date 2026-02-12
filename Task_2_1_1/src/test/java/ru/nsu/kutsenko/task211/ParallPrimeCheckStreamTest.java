package ru.nsu.kutsenko.task211;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

class ParallPrimeCheckStreamTest {

    @Test
    void testAllPrimesReturnsFalse() {
        int[] primes = {2, 3, 5, 7, 11, 13, 17, 19};
        assertFalse(ParallPrimeCheckStream.withoutPrime(primes));
    }

    @Test
    void testOneNonPrimeReturnsTrue() {
        int[] numbers = {2, 3, 5, 7, 8, 11, 13};
        assertTrue(ParallPrimeCheckStream.withoutPrime(numbers));
    }

    @Test
    void testAllNonPrimesReturnsTrue() {
        int[] nonPrimes = {4, 6, 8, 9, 10, 12, 14, 15};
        assertTrue(ParallPrimeCheckStream.withoutPrime(nonPrimes));
    }

    @Test
    void testEmptyArrayReturnsFalse() {
        int[] empty = {};
        assertFalse(ParallPrimeCheckStream.withoutPrime(empty));
    }

    @Test
    void testSinglePrimeReturnsFalse() {
        int[] singlePrime = {17};
        assertFalse(ParallPrimeCheckStream.withoutPrime(singlePrime));
    }

    @Test
    void testSingleNonPrimeReturnsTrue() {
        int[] singleNonPrime = {4};
        assertTrue(ParallPrimeCheckStream.withoutPrime(singleNonPrime));
    }

    @Test
    void testArrayWithOneAndNegativeReturnsTrue() {
        int[] numbers = {2, 3, 5, 1, 7, 11};
        assertTrue(ParallPrimeCheckStream.withoutPrime(numbers));
    }

    @Test
    void testArrayWithZeroReturnsTrue() {
        int[] numbers = {2, 3, 5, 0, 7, 11};
        assertTrue(ParallPrimeCheckStream.withoutPrime(numbers));
    }

    @Test
    void testLargePrimesArrayReturnsFalse() {
        int[] largePrimes = {
            1_000_000_007,
            1_000_000_009,
            1_000_000_021,
            1_000_000_033
        };
        assertFalse(ParallPrimeCheckStream.withoutPrime(largePrimes));
    }

    @Test
    void testVeryLargeArrayAllPrimesReturnsFalse() {
        int size = 100_000;
        int[] largeArray = new int[size];
        for (int i = 0; i < size; i++) {
            largeArray[i] = 1_000_000_007;
        }
        assertFalse(ParallPrimeCheckStream.withoutPrime(largeArray));
    }

    @Test
    void testVeryLargeArrayWithNonPrimeAtEndReturnsTrue() {
        int size = 100_000;
        int[] largeArray = new int[size];
        for (int i = 0; i < size - 1; i++) {
            largeArray[i] = 1_000_000_007;
        }
        largeArray[size - 1] = 4;
        assertTrue(ParallPrimeCheckStream.withoutPrime(largeArray));
    }
}