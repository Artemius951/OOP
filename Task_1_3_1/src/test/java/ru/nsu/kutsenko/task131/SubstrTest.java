package ru.nsu.kutsenko.task131;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Тестовый класс для проверки реализации поиска подстроки.
 */
public class SubstrTest {

    private File testFile;

    @BeforeEach
    void setUp() throws IOException {
        testFile = File.createTempFile("test", ".txt");
        testFile.deleteOnExit();
    }

    @AfterEach
    void tearDown() {
        if (testFile.exists()) {
            testFile.delete();
        }
    }

    @Test
    void testBasicSubstringSearch() throws IOException {
        // Тест: "абракадабра" и "бра"
        writeToFile("абракадабра");
        int[] result = Substr.find(testFile.getAbsolutePath(), "бра");
        assertArrayEquals(new int[]{1, 8}, result);
    }

    /**
     * Вспомогательный метод для записи строки в тестовый файл.
     *
     * @param content содержимое для записи в файл
     * @throws IOException если произошла ошибка записи
     */
    private void writeToFile(String content) throws IOException {
        try (Writer writer = new OutputStreamWriter(
            new FileOutputStream(testFile), StandardCharsets.UTF_8)) {
            writer.write(content);
        }
    }

    @Test
    void testWholeFileMatch() throws IOException {
        writeToFile("abra");
        assertArrayEquals(new int[]{0}, Substr.find(testFile.getAbsolutePath(), "abra"));
    }

    @Test
    void testPatternLongerThanFile() throws IOException {
        writeToFile("абра");
        assertArrayEquals(new int[0], Substr.find(testFile.getAbsolutePath(), "кадабра"));
    }

    @Test
    void testPatternNotFound() throws IOException {
        writeToFile("абракадабра");
        assertArrayEquals(new int[0], Substr.find(testFile.getAbsolutePath(), "злогин"));
    }

    @Test
    void testOverlappingOccurrences() throws IOException {
        writeToFile("aaaaa");
        assertArrayEquals(new int[]{0, 1, 2, 3}, Substr.find(testFile.getAbsolutePath(),
            "aa"));
    }

    @Test
    void testSingleCharacterPattern() throws IOException {
        writeToFile("абракадабра");
        assertArrayEquals(new int[]{0, 3, 5, 7, 10}, Substr.find(testFile.getAbsolutePath(),
            "а"));
    }


    @Test
    void testEmptyPattern() throws IOException {
        writeToFile("абра");
        assertArrayEquals(new int[0], Substr.find(testFile.getAbsolutePath(), ""));
    }

    @Test
    void testNonExistingFile() {
        assertThrows(IOException.class, () -> Substr.find("no_such_file.txt",
            "abra"));

    }

    @Test
    void testArabic() throws IOException {
        writeToFile("اللغة العربية جميلة");
        assertArrayEquals(new int[]{8}, Substr.find(testFile.getAbsolutePath(), "عربية"));
    }

    @Test
    void testChinese() throws IOException {
        writeToFile("中文测试很有趣");
        assertArrayEquals(new int[]{2}, Substr.find(testFile.getAbsolutePath(), "测试"));
    }

    @Test
    void testJapanese() throws IOException {
        writeToFile("日本語のテストです");
        assertArrayEquals(new int[]{4}, Substr.find(testFile.getAbsolutePath(), "テスト"));
    }

    @Test
    void testKorean() throws IOException {
        writeToFile("한국어 테스트입니다");
        assertArrayEquals(new int[]{4}, Substr.find(testFile.getAbsolutePath(), "테스트"));
    }

    @Test
    void testGreek() throws IOException {
        writeToFile("Οδυσσέας ο ήρως της Οδύσσειας");
        assertArrayEquals(new int[]{0}, Substr.find(testFile.getAbsolutePath(), "Οδυσ"));
        assertArrayEquals(new int[]{20}, Substr.find(testFile.getAbsolutePath(), "Οδύσ"));
    }

    @Test
    void testVikingCall() throws IOException {
        writeToFile("Valhall väntar, älgar skriar");
        assertArrayEquals(new int[]{0}, Substr.find(testFile.getAbsolutePath(), "Valhall"));
        assertArrayEquals(new int[]{16}, Substr.find(testFile.getAbsolutePath(), "älgar"));
    }

    @Test
    void testGermanUmlauts() throws IOException {
        writeToFile("München Füße Über");
        assertArrayEquals(new int[]{0}, Substr.find(testFile.getAbsolutePath(), "München"));
        assertArrayEquals(new int[]{8}, Substr.find(testFile.getAbsolutePath(), "Füße"));
    }

    @Test
    void testMoriaGateInscription() throws IOException {
        writeToFile("Ennyn Durin Aran Moria. Pedo mellon a minno.");
        assertArrayEquals(new int[]{0}, Substr.find(testFile.getAbsolutePath(), "Ennyn"));
        assertArrayEquals(new int[]{6}, Substr.find(testFile.getAbsolutePath(), "Durin"));
        assertArrayEquals(new int[]{12}, Substr.find(testFile.getAbsolutePath(), "Aran"));
        assertArrayEquals(new int[]{17}, Substr.find(testFile.getAbsolutePath(), "Moria"));
    }

    @Test
    void testVeryLargeFile() throws IOException {StringBuilder largeContent = new StringBuilder();
        for (int i = 0; i < 100000; i++) {
            largeContent.append("abcde");
        }
        String pattern = "cde";
        writeToFile(largeContent.toString());

        int[] result = Substr.find(testFile.getAbsolutePath(), pattern);
        assertTrue(result.length > 1000);
    }

    @Test
    void testPatternAtVeryEnd() throws IOException {
        writeToFile("start end");
        assertArrayEquals(new int[]{6}, Substr.find(testFile.getAbsolutePath(), "end"));
    }

    @Test
    void testFileWithOnlyNewlines() throws IOException {
        writeToFile("\n\n\n\n\n");
        assertArrayEquals(new int[]{0,1,2,3,4}, Substr.find(testFile.getAbsolutePath(), "\n"));
    }

    @Test
    void testEmptyFileWithPattern() throws IOException {
        writeToFile("");
        assertArrayEquals(new int[0], Substr.find(testFile.getAbsolutePath(), "any"));
    }

    @Test
    void testMultipleEncodingsMixed() throws IOException {
        writeToFile("ASCII 中文 ελληνικά العربية");
        assertArrayEquals(new int[]{6}, Substr.find(testFile.getAbsolutePath(), "中文"));
        assertArrayEquals(new int[]{9}, Substr.find(testFile.getAbsolutePath(), "ελλην"));
    }
}