package ru.nsu.kutsenko.task122;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Тестовый класс для проверки реализации хеш-таблицы.
 */
public class HashTableTest {
    private HashTable<String, Integer> table;


    @BeforeEach
    void setUp() {
        table = new HashTable<>();
    }

    @Test
    void testPutAndGet() {
        assertTrue(table.put("key1", 1));
        assertTrue(table.put("key2", 2));
        assertTrue(table.put("key3", 3));

        assertEquals(1, table.get("key1"));
        assertEquals(2, table.get("key2"));
        assertEquals(3, table.get("key3"));
        assertNull(table.get("nonexistent"));
    }

    @Test
    void testPutUpdate() {
        table.put("key1", 1);
        table.put("key1", 100);

        assertEquals(100, table.get("key1"));
    }

    @Test
    void testRemove() {
        table.put("key1", 1);
        table.put("key2", 2);

        assertTrue(table.remove("key1"));
        assertFalse(table.remove("key1"));
        assertNull(table.get("key1"));
        assertEquals(2, table.get("key2"));
    }

    @Test
    void testContainsKey() {
        table.put("key1", 1);
        table.put("key2", 2);

        assertTrue(table.containsKey("key1"));
        assertTrue(table.containsKey("key2"));
        assertFalse(table.containsKey("key3"));
    }

    @Test
    void testNullKey() {
        assertTrue(table.put(null, 42));
        assertEquals(42, table.get(null));
        assertTrue(table.containsKey(null));

        assertTrue(table.remove(null));
        assertNull(table.get(null));
        assertFalse(table.containsKey(null));
    }

    @Test
    void testResize() {
        for (int i = 0; i < 20; i++) {
            table.put("key" + i, i);
        }

        for (int i = 0; i < 20; i++) {
            assertEquals(i, table.get("key" + i));
        }
    }



    @Test
    void testIteratorEmpty() {
        Iterator<HashTable.Pair<String, Integer>> iterator = table.iterator();
        assertFalse(iterator.hasNext());
        assertThrows(NoSuchElementException.class, iterator::next);
    }

    @Test
    void testIteratorConcurrentModification() {
        table.put("key1", 1);
        table.put("key2", 2);

        Iterator<HashTable.Pair<String, Integer>> iterator = table.iterator();
        table.put("key3", 3);

        assertThrows(ConcurrentModificationException.class, iterator::hasNext);
        assertThrows(ConcurrentModificationException.class, iterator::next);
    }

    @Test
    void testEquals() {
        HashTable<String, Integer> table1 = new HashTable<>();
        table1.put("key1", 1);
        table1.put("key2", 2);

        HashTable<String, Integer> table2 = new HashTable<>();
        table2.put("key1", 1);
        table2.put("key2", 2);

        HashTable<String, Integer> table3 = new HashTable<>();
        table3.put("key1", 1);
        table3.put("key2", 3);

        HashTable<String, Integer> table4 = new HashTable<>();
        table4.put("key1", 1);

        assertEquals(table1, table2);
        assertNotEquals(table1, table3);
        assertNotEquals(table1, table4);
        assertEquals(table1, table1);
        assertNotEquals(table1, null);
        assertNotEquals(table1, "not a table");
    }

    @Test
    void testHashCode() {
        HashTable<String, Integer> table1 = new HashTable<>();
        table1.put("key1", 1);
        table1.put("key2", 2);

        HashTable<String, Integer> table2 = new HashTable<>();
        table2.put("key1", 1);
        table2.put("key2", 2);

        HashTable<String, Integer> table3 = new HashTable<>();
        table3.put("key1", 1);
        table3.put("key2", 3);

        assertEquals(table1.hashCode(), table2.hashCode());
        assertNotEquals(table1.hashCode(), table3.hashCode());
    }

    @Test
    void testToString() {
        table.put("key1", 1);
        table.put("key2", 2);

        String result = table.toString();
        assertTrue(result.startsWith("{"));
        assertTrue(result.endsWith("}"));
        assertTrue(result.contains("key1=1"));
        assertTrue(result.contains("key2=2"));
    }

    @Test
    void testCollisions() {
        table.put("Aa", 1);
        table.put("BB", 2);

        assertEquals(1, table.get("Aa"));
        assertEquals(2, table.get("BB"));

        assertTrue(table.remove("Aa"));
        assertNull(table.get("Aa"));
        assertEquals(2, table.get("BB"));
    }

    @Test
    void testUpdateMethod() {
        table.put("key1", 1);

        assertTrue(table.update("key1", 100));
        assertEquals(100, table.get("key1"));

        assertFalse(table.update("nonexistent", 999));
        assertNull(table.get("nonexistent"));
    }

    @Test
    void testEmptyTable() {
        assertNull(table.get("any"));
        assertFalse(table.containsKey("any"));
        assertFalse(table.remove("any"));
        assertEquals(0, table.toString().length() - 2); // только {}
    }


}