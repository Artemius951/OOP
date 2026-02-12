package ru.nsu.kutsenko.task211;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;



class PrimalityTest {

    @Test
    void testIsPrimeWithSmallPrimes() {
        assertTrue(Primality.isPrime(2));
        assertTrue(Primality.isPrime(3));
        assertTrue(Primality.isPrime(5));
        assertTrue(Primality.isPrime(7));
        assertTrue(Primality.isPrime(11));
        assertTrue(Primality.isPrime(13));
    }

    @Test
    void testIsPrimeWithLargePrimes() {
        assertTrue(Primality.isPrime(1_000_000_007));
        assertTrue(Primality.isPrime(1_000_000_009));
        assertTrue(Primality.isPrime(1_000_000_021));
        assertTrue(Primality.isPrime(1_000_000_033));
    }

    @Test
    void testIsPrimeWithNonPrimes() {
        assertFalse(Primality.isPrime(1));
        assertFalse(Primality.isPrime(0));
        assertFalse(Primality.isPrime(-1));
        assertFalse(Primality.isPrime(-10));
        assertFalse(Primality.isPrime(4));
        assertFalse(Primality.isPrime(6));
        assertFalse(Primality.isPrime(8));
        assertFalse(Primality.isPrime(9));
        assertFalse(Primality.isPrime(10));
        assertFalse(Primality.isPrime(100));
    }

    @Test
    void testIsPrimeWithEvenNumbers() {
        assertFalse(Primality.isPrime(2_000_000_000));
        assertFalse(Primality.isPrime(100_000_000));
        assertFalse(Primality.isPrime(1000));
    }

    @ParameterizedTest
    @ValueSource(ints = {2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47})
    void testIsPrimeWithParameterizedPrimes(int prime) {
        assertTrue(Primality.isPrime(prime));
    }

    @ParameterizedTest
    @ValueSource(ints = {4, 6, 8, 9, 10, 12, 14, 15, 16, 18, 20, 21, 22, 24, 25})
    void testIsPrimeWithParameterizedNonPrimes(int nonPrime) {
        assertFalse(Primality.isPrime(nonPrime));
    }
}