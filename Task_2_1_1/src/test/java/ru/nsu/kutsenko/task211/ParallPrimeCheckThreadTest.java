package ru.nsu.kutsenko.task211;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

class ParallPrimeCheckThreadTest {

    @Test
    void testAllPrimesReturnsFalse() throws InterruptedException {
        int[] primes = {2, 3, 5, 7, 11, 13, 17, 19};
        ParallPrimeCheckThread checker = new ParallPrimeCheckThread(primes, 4);
        assertFalse(checker.withoutPrime());
    }

    @Test
    void testOneNonPrimeReturnsTrue() throws InterruptedException {
        int[] numbers = {2, 3, 5, 7, 8, 11, 13};
        ParallPrimeCheckThread checker = new ParallPrimeCheckThread(numbers, 4);
        assertTrue(checker.withoutPrime());
    }

    @Test
    void testAllNonPrimesReturnsTrue() throws InterruptedException {
        int[] nonPrimes = {4, 6, 8, 9, 10, 12, 14, 15};
        ParallPrimeCheckThread checker = new ParallPrimeCheckThread(nonPrimes, 4);
        assertTrue(checker.withoutPrime());
    }

    @Test
    void testEmptyArrayReturnsFalse() throws InterruptedException {
        int[] empty = {};
        ParallPrimeCheckThread checker = new ParallPrimeCheckThread(empty, 4);
        assertFalse(checker.withoutPrime());
    }

    @Test
    void testSinglePrimeReturnsFalse() throws InterruptedException {
        int[] singlePrime = {17};
        ParallPrimeCheckThread checker = new ParallPrimeCheckThread(singlePrime, 4);
        assertFalse(checker.withoutPrime());
    }

    @Test
    void testSingleNonPrimeReturnsTrue() throws InterruptedException {
        int[] singleNonPrime = {4};
        ParallPrimeCheckThread checker = new ParallPrimeCheckThread(singleNonPrime, 4);
        assertTrue(checker.withoutPrime());
    }

    @Test
    void testArrayWithOneReturnsTrue() throws InterruptedException {
        int[] numbers = {2, 3, 5, 1, 7, 11};
        ParallPrimeCheckThread checker = new ParallPrimeCheckThread(numbers, 4);
        assertTrue(checker.withoutPrime());
    }

    @Test
    void testArrayWithZeroReturnsTrue() throws InterruptedException {
        int[] numbers = {2, 3, 5, 0, 7, 11};
        ParallPrimeCheckThread checker = new ParallPrimeCheckThread(numbers, 4);
        assertTrue(checker.withoutPrime());
    }

    @Test
    void testArrayWithNegativeReturnsTrue() throws InterruptedException {
        int[] numbers = {2, 3, 5, -7, 11, 13};
        ParallPrimeCheckThread checker = new ParallPrimeCheckThread(numbers, 4);
        assertTrue(checker.withoutPrime());
    }

    @Test
    void testLargePrimesArrayReturnsFalse() throws InterruptedException {
        int[] largePrimes = {
            1_000_000_007,
            1_000_000_009,
            1_000_000_021,
            1_000_000_033
        };
        ParallPrimeCheckThread checker = new ParallPrimeCheckThread(largePrimes, 4);
        assertFalse(checker.withoutPrime());
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3, 4, 5, 6, 7, 8, 16, 32})
    void testDifferentThreadCountsWithAllPrimes(int threadCount) throws InterruptedException {
        int[] primes = {2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37};
        ParallPrimeCheckThread checker = new ParallPrimeCheckThread(primes, threadCount);
        assertFalse(checker.withoutPrime());
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3, 4, 5, 6, 7, 8, 16, 32})
    void testDifferentThreadCountsWithNonPrime(int threadCount) throws InterruptedException {
        int[] numbers = {2, 3, 5, 7, 11, 13, 17, 19, 4, 23, 29, 31};
        ParallPrimeCheckThread checker = new ParallPrimeCheckThread(numbers, threadCount);
        assertTrue(checker.withoutPrime());
    }

    @Test
    void testThreadCountZero() throws InterruptedException {
        int[] primes = {2, 3, 5, 7, 11};
        ParallPrimeCheckThread checker = new ParallPrimeCheckThread(primes, 0);
        assertFalse(checker.withoutPrime());
    }

    @Test
    void testThreadCountNegative() throws InterruptedException {
        int[] primes = {2, 3, 5, 7, 11};
        ParallPrimeCheckThread checker = new ParallPrimeCheckThread(primes, -5);
        assertFalse(checker.withoutPrime());
    }

    @Test
    void testMoreThreadsThanElements() throws InterruptedException {
        int[] primes = {2, 3, 5, 7, 11};
        ParallPrimeCheckThread checker = new ParallPrimeCheckThread(primes, 10);
        assertFalse(checker.withoutPrime());
    }

    @Test
    void testNonPrimeInFirstChunk() throws InterruptedException {
        int[] numbers = {4, 2, 3, 5, 7, 11, 13, 17, 19, 23};
        ParallPrimeCheckThread checker = new ParallPrimeCheckThread(numbers, 4);
        assertTrue(checker.withoutPrime());
    }

    @Test
    void testNonPrimeInMiddleChunk() throws InterruptedException {
        int[] numbers = {2, 3, 5, 7, 4, 11, 13, 17, 19, 23};
        ParallPrimeCheckThread checker = new ParallPrimeCheckThread(numbers, 4);
        assertTrue(checker.withoutPrime());
    }

    @Test
    void testNonPrimeInLastChunk() throws InterruptedException {
        int[] numbers = {2, 3, 5, 7, 11, 13, 17, 19, 23, 4};
        ParallPrimeCheckThread checker = new ParallPrimeCheckThread(numbers, 4);
        assertTrue(checker.withoutPrime());
    }

    @Test
    void testArraySizeExactlyDivisibleByThreadCount() throws InterruptedException {
        int[] primes = {2, 3, 5, 7, 11, 13, 17, 19};
        ParallPrimeCheckThread checker = new ParallPrimeCheckThread(primes, 4);
        assertFalse(checker.withoutPrime());
    }

    @Test
    void testArraySizeNotDivisibleByThreadCount() throws InterruptedException {
        int[] primes = {2, 3, 5, 7, 11, 13, 17, 19, 23};
        ParallPrimeCheckThread checker = new ParallPrimeCheckThread(primes, 4);
        assertFalse(checker.withoutPrime());
    }

    @Test
    void testVeryLargeArrayAllPrimes() throws InterruptedException {
        int size = 100_000;
        int[] largeArray = new int[size];
        for (int i = 0; i < size; i++) {
            largeArray[i] = 1_000_000_007;
        }
        ParallPrimeCheckThread checker = new ParallPrimeCheckThread(largeArray, 8);
        assertFalse(checker.withoutPrime());
    }

    @Test
    void testVeryLargeArrayWithNonPrimeAtBeginning() throws InterruptedException {
        int size = 100_000;
        int[] largeArray = new int[size];
        largeArray[0] = 4;
        for (int i = 1; i < size; i++) {
            largeArray[i] = 1_000_000_007;
        }
        ParallPrimeCheckThread checker = new ParallPrimeCheckThread(largeArray, 8);
        assertTrue(checker.withoutPrime());
    }

    @Test
    void testVeryLargeArrayWithNonPrimeAtEnd() throws InterruptedException {
        int size = 100_000;
        int[] largeArray = new int[size];
        for (int i = 0; i < size - 1; i++) {
            largeArray[i] = 1_000_000_007;
        }
        largeArray[size - 1] = 4;
        ParallPrimeCheckThread checker = new ParallPrimeCheckThread(largeArray, 8);
        assertTrue(checker.withoutPrime());
    }

    @Test
    void testVeryLargeArrayWithNonPrimeInMiddle() throws InterruptedException {
        int size = 100_000;
        int[] largeArray = new int[size];
        for (int i = 0; i < size; i++) {
            largeArray[i] = 1_000_000_007;
        }
        largeArray[size / 2] = 4;
        ParallPrimeCheckThread checker = new ParallPrimeCheckThread(largeArray, 8);
        assertTrue(checker.withoutPrime());
    }

    @Test
    void testMultipleNonPrimes() throws InterruptedException {
        int[] numbers = {4, 6, 8, 9, 2, 3, 5, 7, 10, 12};
        ParallPrimeCheckThread checker = new ParallPrimeCheckThread(numbers, 4);
        assertTrue(checker.withoutPrime());
    }

    @Test
    void testArrayWithAllSameLargePrime() throws InterruptedException {
        int[] numbers = new int[1000];
        for (int i = 0; i < 1000; i++) {
            numbers[i] = 1_000_000_007;
        }
        ParallPrimeCheckThread checker = new ParallPrimeCheckThread(numbers, 4);
        assertFalse(checker.withoutPrime());
    }

    @Test
    void testArrayWithAllSameNonPrime() throws InterruptedException {
        int[] numbers = new int[1000];
        for (int i = 0; i < 1000; i++) {
            numbers[i] = 1000000;
        }
        ParallPrimeCheckThread checker = new ParallPrimeCheckThread(numbers, 4);
        assertTrue(checker.withoutPrime());
    }

    @Test
    void testThreadCountGreaterThanArrayLength() throws InterruptedException {
        int[] primes = {2, 3, 5};
        ParallPrimeCheckThread checker = new ParallPrimeCheckThread(primes, 10);
        assertFalse(checker.withoutPrime());
    }

    @Test
    void testInterleavedPrimesAndNonPrimes() throws InterruptedException {
        int[] numbers = {2, 4, 3, 6, 5, 8, 7, 9, 11, 12};
        ParallPrimeCheckThread checker = new ParallPrimeCheckThread(numbers, 4);
        assertTrue(checker.withoutPrime());
    }
}