package ru.nsu.kutsenko.task211;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class SeqPrimeCheckTest {

    @Test
    void testAllPrimesReturnsFalse() {
        int[] primes = {2, 3, 5, 7, 11, 13, 17, 19};
        SeqPrimeCheck checker = new SeqPrimeCheck(primes);
        assertFalse(checker.withoutPrime());
    }

    @Test
    void testOneNonPrimeReturnsTrue() {
        int[] numbers = {2, 3, 5, 7, 8, 11, 13};
        SeqPrimeCheck checker = new SeqPrimeCheck(numbers);
        assertTrue(checker.withoutPrime());
    }

    @Test
    void testAllNonPrimesReturnsTrue() {
        int[] nonPrimes = {4, 6, 8, 9, 10, 12, 14, 15};
        SeqPrimeCheck checker = new SeqPrimeCheck(nonPrimes);
        assertTrue(checker.withoutPrime());
    }

    @Test
    void testEmptyArrayReturnsFalse() {
        int[] empty = {};
        SeqPrimeCheck checker = new SeqPrimeCheck(empty);
        assertFalse(checker.withoutPrime());
    }

    @Test
    void testSinglePrimeReturnsFalse() {
        int[] singlePrime = {17};
        SeqPrimeCheck checker = new SeqPrimeCheck(singlePrime);
        assertFalse(checker.withoutPrime());
    }

    @Test
    void testSingleNonPrimeReturnsTrue() {
        int[] singleNonPrime = {4};
        SeqPrimeCheck checker = new SeqPrimeCheck(singleNonPrime);
        assertTrue(checker.withoutPrime());
    }

    @Test
    void testArrayWithOneAndNegativeReturnsTrue() {
        int[] numbers = {2, 3, 5, 1, 7, 11};
        SeqPrimeCheck checker = new SeqPrimeCheck(numbers);
        assertTrue(checker.withoutPrime());
    }

    @Test
    void testArrayWithZeroReturnsTrue() {
        int[] numbers = {2, 3, 5, 0, 7, 11};
        SeqPrimeCheck checker = new SeqPrimeCheck(numbers);
        assertTrue(checker.withoutPrime());
    }

    @Test
    void testLargePrimesArrayReturnsFalse() {
        int[] largePrimes = {
            1_000_000_007,
            1_000_000_009,
            1_000_000_021,
            1_000_000_033
        };
        SeqPrimeCheck checker = new SeqPrimeCheck(largePrimes);
        assertFalse(checker.withoutPrime());
    }

    @Test
    void testNonPrimeAtBeginningReturnsTrue() {
        int[] numbers = {4, 2, 3, 5, 7};
        SeqPrimeCheck checker = new SeqPrimeCheck(numbers);
        assertTrue(checker.withoutPrime());
    }

    @Test
    void testNonPrimeAtEndReturnsTrue() {
        int[] numbers = {2, 3, 5, 7, 8};
        SeqPrimeCheck checker = new SeqPrimeCheck(numbers);
        assertTrue(checker.withoutPrime());
    }
}