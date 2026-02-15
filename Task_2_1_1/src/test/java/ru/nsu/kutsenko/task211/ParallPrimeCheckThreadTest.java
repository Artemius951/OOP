package ru.nsu.kutsenko.task211;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class ParallPrimeCheckThreadTest {

    @Test
    void testAllPrimesReturnsFalse() throws InterruptedException {
        int[] primes = {2, 3, 5, 7, 11, 13, 17, 19};
        assertFalse(ParallPrimeCheckThread.withoutPrime(primes, 4));
    }

    @Test
    void testOneNonPrimeReturnsTrue() throws InterruptedException {
        int[] numbers = {2, 3, 5, 7, 8, 11, 13};
        assertTrue(ParallPrimeCheckThread.withoutPrime(numbers, 4));
    }

    @Test
    void testAllNonPrimesReturnsTrue() throws InterruptedException {
        int[] nonPrimes = {4, 6, 8, 9, 10, 12, 14, 15};
        assertTrue(ParallPrimeCheckThread.withoutPrime(nonPrimes, 4));
    }

    @Test
    void testEmptyArrayReturnsFalse() throws InterruptedException {
        int[] empty = {};
        assertFalse(ParallPrimeCheckThread.withoutPrime(empty, 4));
    }

    @Test
    void testSinglePrimeReturnsFalse() throws InterruptedException {
        int[] singlePrime = {17};
        assertFalse(ParallPrimeCheckThread.withoutPrime(singlePrime, 4));
    }

    @Test
    void testSingleNonPrimeReturnsTrue() throws InterruptedException {
        int[] singleNonPrime = {4};
        assertTrue(ParallPrimeCheckThread.withoutPrime(singleNonPrime, 4));
    }

    @Test
    void testArrayWithOneReturnsTrue() throws InterruptedException {
        int[] numbers = {2, 3, 5, 1, 7, 11};
        assertTrue(ParallPrimeCheckThread.withoutPrime(numbers, 4));
    }

    @Test
    void testArrayWithZeroReturnsTrue() throws InterruptedException {
        int[] numbers = {2, 3, 5, 0, 7, 11};
        assertTrue(ParallPrimeCheckThread.withoutPrime(numbers, 4));
    }

    @Test
    void testArrayWithNegativeReturnsTrue() throws InterruptedException {
        int[] numbers = {2, 3, 5, -7, 11, 13};
        assertTrue(ParallPrimeCheckThread.withoutPrime(numbers, 4));
    }

    @Test
    void testLargePrimesArrayReturnsFalse() throws InterruptedException {
        int[] largePrimes = {
            1_000_000_007,
            1_000_000_009,
            1_000_000_021,
            1_000_000_033
        };
        assertFalse(ParallPrimeCheckThread.withoutPrime(largePrimes, 4));
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3, 4, 5, 6, 7, 8, 16, 32})
    void testDifferentThreadCountsWithAllPrimes(int threadCount) throws InterruptedException {
        int[] primes = {2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37};
        assertFalse(ParallPrimeCheckThread.withoutPrime(primes, threadCount));
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3, 4, 5, 6, 7, 8, 16, 32})
    void testDifferentThreadCountsWithNonPrime(int threadCount) throws InterruptedException {
        int[] numbers = {2, 3, 5, 7, 11, 13, 17, 19, 4, 23, 29, 31};
        assertTrue(ParallPrimeCheckThread.withoutPrime(numbers, threadCount));
    }

    @Test
    void testThreadCountZeroThrowsException() {
        int[] primes = {2, 3, 5, 7, 11};
        assertThrows(IllegalArgumentException.class, () -> {
            ParallPrimeCheckThread.withoutPrime(primes, 0);
        });
    }

    @Test
    void testThreadCountNegativeThrowsException() {
        int[] primes = {2, 3, 5, 7, 11};
        assertThrows(IllegalArgumentException.class, () -> {
            ParallPrimeCheckThread.withoutPrime(primes, -5);
        });
    }

    @Test
    void testMoreThreadsThanElements() throws InterruptedException {
        int[] primes = {2, 3, 5, 7, 11};
        assertFalse(ParallPrimeCheckThread.withoutPrime(primes, 10));
    }

    @Test
    void testNonPrimeInFirstChunk() throws InterruptedException {
        int[] numbers = {4, 2, 3, 5, 7, 11, 13, 17, 19, 23};
        assertTrue(ParallPrimeCheckThread.withoutPrime(numbers, 4));
    }

    @Test
    void testNonPrimeInMiddleChunk() throws InterruptedException {
        int[] numbers = {2, 3, 5, 7, 4, 11, 13, 17, 19, 23};
        assertTrue(ParallPrimeCheckThread.withoutPrime(numbers, 4));
    }

    @Test
    void testNonPrimeInLastChunk() throws InterruptedException {
        int[] numbers = {2, 3, 5, 7, 11, 13, 17, 19, 23, 4};
        assertTrue(ParallPrimeCheckThread.withoutPrime(numbers, 4));
    }

    @Test
    void testArraySizeExactlyDivisibleByThreadCount() throws InterruptedException {
        int[] primes = {2, 3, 5, 7, 11, 13, 17, 19};
        assertFalse(ParallPrimeCheckThread.withoutPrime(primes, 4));
    }

    @Test
    void testArraySizeNotDivisibleByThreadCount() throws InterruptedException {
        int[] primes = {2, 3, 5, 7, 11, 13, 17, 19, 23};
        assertFalse(ParallPrimeCheckThread.withoutPrime(primes, 4));
    }

    @Test
    void testVeryLargeArrayAllPrimes() throws InterruptedException {
        int size = 100_000;
        int[] largeArray = new int[size];
        for (int i = 0; i < size; i++) {
            largeArray[i] = 1_000_000_007;
        }
        assertFalse(ParallPrimeCheckThread.withoutPrime(largeArray, 8));
    }

    @Test
    void testVeryLargeArrayWithNonPrimeAtBeginning() throws InterruptedException {
        int size = 100_000;
        int[] largeArray = new int[size];
        largeArray[0] = 4;
        for (int i = 1; i < size; i++) {
            largeArray[i] = 1_000_000_007;
        }
        assertTrue(ParallPrimeCheckThread.withoutPrime(largeArray, 8));
    }

    @Test
    void testVeryLargeArrayWithNonPrimeAtEnd() throws InterruptedException {
        int size = 100_000;
        int[] largeArray = new int[size];
        for (int i = 0; i < size - 1; i++) {
            largeArray[i] = 1_000_000_007;
        }
        largeArray[size - 1] = 4;
        assertTrue(ParallPrimeCheckThread.withoutPrime(largeArray, 8));
    }

    @Test
    void testVeryLargeArrayWithNonPrimeInMiddle() throws InterruptedException {
        int size = 100_000;
        int[] largeArray = new int[size];
        for (int i = 0; i < size; i++) {
            largeArray[i] = 1_000_000_007;
        }
        largeArray[size / 2] = 4;
        assertTrue(ParallPrimeCheckThread.withoutPrime(largeArray, 8));
    }

    @Test
    void testMultipleNonPrimes() throws InterruptedException {
        int[] numbers = {4, 6, 8, 9, 2, 3, 5, 7, 10, 12};
        assertTrue(ParallPrimeCheckThread.withoutPrime(numbers, 4));
    }

    @Test
    void testArrayWithAllSameLargePrime() throws InterruptedException {
        int[] numbers = new int[1000];
        for (int i = 0; i < 1000; i++) {
            numbers[i] = 1_000_000_007;
        }
        assertFalse(ParallPrimeCheckThread.withoutPrime(numbers, 4));
    }

    @Test
    void testArrayWithAllSameNonPrime() throws InterruptedException {
        int[] numbers = new int[1000];
        for (int i = 0; i < 1000; i++) {
            numbers[i] = 1000000;
        }
        assertTrue(ParallPrimeCheckThread.withoutPrime(numbers, 4));
    }

    @Test
    void testThreadCountGreaterThanArrayLength() throws InterruptedException {
        int[] primes = {2, 3, 5};
        assertFalse(ParallPrimeCheckThread.withoutPrime(primes, 10));
    }

    @Test
    void testInterleavedPrimesAndNonPrimes() throws InterruptedException {
        int[] numbers = {2, 4, 3, 6, 5, 8, 7, 9, 11, 12};
        assertTrue(ParallPrimeCheckThread.withoutPrime(numbers, 4));
    }
}