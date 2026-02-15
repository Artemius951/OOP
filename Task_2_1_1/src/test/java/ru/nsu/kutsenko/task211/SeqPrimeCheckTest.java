package ru.nsu.kutsenko.task211;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class SeqPrimeCheckTest {

    @Test
    void testAllPrimesReturnsFalse() {
        int[] primes = {2, 3, 5, 7, 11, 13, 17, 19};
        assertFalse(SeqPrimeCheck.withoutPrime(primes));
    }

    @Test
    void testOneNonPrimeReturnsTrue() {
        int[] numbers = {2, 3, 5, 7, 8, 11, 13};
        assertTrue(SeqPrimeCheck.withoutPrime(numbers));
    }

    @Test
    void testAllNonPrimesReturnsTrue() {
        int[] nonPrimes = {4, 6, 8, 9, 10, 12, 14, 15};
        assertTrue(SeqPrimeCheck.withoutPrime(nonPrimes));
    }

    @Test
    void testEmptyArrayReturnsFalse() {
        int[] empty = {};
        assertFalse(SeqPrimeCheck.withoutPrime(empty));
    }

    @Test
    void testSinglePrimeReturnsFalse() {
        int[] singlePrime = {17};
        assertFalse(SeqPrimeCheck.withoutPrime(singlePrime));
    }

    @Test
    void testSingleNonPrimeReturnsTrue() {
        int[] singleNonPrime = {4};
        assertTrue(SeqPrimeCheck.withoutPrime(singleNonPrime));
    }

    @Test
    void testArrayWithOneAndNegativeReturnsTrue() {
        int[] numbers = {2, 3, 5, 1, 7, 11};
        assertTrue(SeqPrimeCheck.withoutPrime(numbers));
    }

    @Test
    void testArrayWithZeroReturnsTrue() {
        int[] numbers = {2, 3, 5, 0, 7, 11};
        assertTrue(SeqPrimeCheck.withoutPrime(numbers));
    }

    @Test
    void testLargePrimesArrayReturnsFalse() {
        int[] largePrimes = {
            1_000_000_007,
            1_000_000_009,
            1_000_000_021,
            1_000_000_033
        };
        assertFalse(SeqPrimeCheck.withoutPrime(largePrimes));
    }

    @Test
    void testNonPrimeAtBeginningReturnsTrue() {
        int[] numbers = {4, 2, 3, 5, 7};
        assertTrue(SeqPrimeCheck.withoutPrime(numbers));
    }

    @Test
    void testNonPrimeAtEndReturnsTrue() {
        int[] numbers = {2, 3, 5, 7, 8};
        assertTrue(SeqPrimeCheck.withoutPrime(numbers));
    }
}