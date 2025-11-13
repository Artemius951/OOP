package ru.nsu.kutsenko.task131;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * Класс для поиска подстроки в больших файлах с использованием алгоритма Рабина-Карпа.
 * Использует потоковое чтение для обработки файлов, превышающих объем ОЗУ.
 */
public class Substr {
    private static final long MODULUS = 1000000007L;
    private static final long BASE = 256;

    /**
     * Находит все вхождения подстроки в файле.
     *
     * @param filename имя файла для поиска
     * @param pattern искомая подстрока
     * @return массив индексов начала каждого вхождения (0-based)
     * @throws IOException если произошла ошибка при чтении файла
     */
    public static int[] find(String filename, String pattern) throws IOException {
        if (pattern == null || pattern.isEmpty()) {
            return new int[0];
        }

        int patternLength = pattern.length();
        long patternHash = computeHash(pattern);
        long basePower = 1;
        for (int i = 0; i < patternLength - 1; i++) {
            basePower = (basePower * BASE) % MODULUS;
        }

        List<Integer> indices = new ArrayList<>();

        try (Reader reader = new InputStreamReader(
            new FileInputStream(filename), StandardCharsets.UTF_8)) {
            int[] window = new int[patternLength];
            long windowHash = 0;
            int windowSize = 0;
            int position = 0;

            int codePoint;
            while ((codePoint = reader.read()) != -1) {
                if (windowSize < patternLength) {
                    window[windowSize] = codePoint;
                    windowSize++;
                    if (windowSize == patternLength) {
                        windowHash = computeHash(window, patternLength);
                    }
                } else {
                    int removeChar = window[0];
                    for (int i = 0; i < patternLength - 1; i++) {
                        window[i] = window[i + 1];
                    }
                    window[patternLength - 1] = codePoint;
                    long removeValue = ((long) removeChar * basePower) % MODULUS;
                    windowHash = ((windowHash - removeValue + MODULUS) % MODULUS);
                    windowHash = (windowHash * BASE + (long) codePoint) % MODULUS;
                }

                if (windowSize == patternLength && windowHash == patternHash) {
                    if (matchesPattern(window, pattern, patternLength)) {
                        indices.add(position - patternLength + 1);
                    }
                }

                position++;
            }
        }

        int[] result = new int[indices.size()];
        for (int i = 0; i < indices.size(); i++) {
            result[i] = indices.get(i);
        }
        return result;
    }

    /**
     * Вычисляет хеш строки по алгоритму Рабина-Карпа.
     */
    private static long computeHash(String text) {
        long hash = 0;
        for (int i = 0; i < text.length(); i++) {
            hash = (hash * BASE + (long) text.codePointAt(i)) % MODULUS;
        }
        return hash;
    }

    /**
     * Вычисляет хеш массива код-поинтов.
     */
    private static long computeHash(int[] codePoints, int length) {
        long hash = 0;
        for (int i = 0; i < length; i++) {
            hash = (hash * BASE + (long) codePoints[i]) % MODULUS;
        }
        return hash;
    }

    /**
     * Проверяет, совпадает ли окно с паттерном.
     */
    private static boolean matchesPattern(int[] window, String pattern, int length) {
        for (int i = 0; i < length; i++) {
            if (window[i] != pattern.codePointAt(i)) {
                return false;
            }
        }
        return true;
    }
}