package ru.nsu.kutsenko.task122;

import java.util.Iterator;

/**
 * Параметризованная хеш-таблица с методами добавления, удаления,
 * поиска и итерации по парам ключ-значение. Обрабатывает коллизии
 * методом цепочек и поддерживает fail-fast итератор.
 */
public class HashTable<K, V> implements Iterable<HashTable.Pair<K, V>> {

    /**
     * Узел цепочки для бакета: хранит ключ, значение и ссылку на следующий узел.
     */
    public static class Pair<K, V> {
        final K key;
        V value;
        Pair<K, V> next;

        /**
         * Создаёт узел пары.
         *
         * @param key ключ
         * @param value значение
         * @param next следующий узел в цепочке
         */
        Pair(K key, V value, Pair<K, V> next) {
            this.key = key;
            this.value = value;
            this.next = next;
        }
    }

    private Pair<K, V>[] table;
    private int size;
    private static final int DEF_CAPACITY = 16;
    private static final double LOAD_FACTOR = 0.75;
    private int modCount = 0;

    /**
     * Создаёт пустую хеш-таблицу с размером по умолчанию.
     */
    public HashTable() {
        table = new Pair[DEF_CAPACITY];
        size = 0;
    }

    /**
     * Вычисляет индекс бакета для ключа по текущей ёмкости таблицы.
     *
     * @param key ключ (может быть null)
     *
     * @return индекс бакета
     */
    private int hash(K key) {
        return hash(key, table.length);
    }

    /**
     * Вычисляет индекс бакета для ключа по заданному модулю.
     *
     * @param key ключ (может быть null)
     * @param mod модуль (ёмкость массива бакетов)
     *
     * @return индекс бакета
     */
    private int hash(K key, int mod) {
        int h = (key == null) ? 0 : key.hashCode();
        return Math.floorMod(h, mod);
    }

    /**
     * Удваивает ёмкость таблицы и перераспределяет элементы по новым бакетам.
     */
    private void resize() {
        int newCapacity = table.length * 2;
        Pair<K, V>[] newTable = new Pair[newCapacity];
        for (int i = 0; i < table.length; i++) {
            Pair<K, V> current = table[i];
            while (current != null) {
                Pair<K, V> next = current.next;
                int idx = hash(current.key, newCapacity);
                current.next = newTable[idx];
                newTable[idx] = current;
                current = next;
            }
        }
        table = newTable;
        modCount++;
    }

    /**
     * Сравнивает ключи с учётом null.
     *
     * @param k1 первый ключ
     * @param k2 второй ключ
     *
     * @return true, если ключи равны
     */
    private boolean equalsKey(K k1, K k2) {
        return (k1 == null && k2 == null) || (k1 != null && k1.equals(k2));
    }

    /**
     * Обновляет значение по ключу, если ключ уже существует.
     *
     * @param key ключ (может быть null)
     * @param value новое значение
     *
     * @return true, если значение было обновлено; false, если ключ не найден
     */
    public boolean update(K key, V value) {
        int id = hash(key);
        Pair<K, V> cur = table[id];
        while (cur != null) {
            if (equalsKey(key, cur.key)) {
                cur.value = value;
                return true;
            }
            cur = cur.next;
        }
        return false;
    }

    /**
     * Добавляет пару ключ-значение. Если ключ уже существует, обновляет значение.
     *
     * @param key ключ (может быть null)
     * @param value значение
     *
     * @return true, если элемент добавлен или обновлён
     */
    public boolean put(K key, V value) {
        if ((double) size / table.length >= LOAD_FACTOR) {
            resize();
        }
        if (update(key, value)) {
            return true;
        }
        int id = hash(key);
        table[id] = new Pair<>(key, value, table[id]);
        size++;
        modCount++;
        return true;
    }



    /**
     * Удаляет пару по ключу.
     *
     * @param key ключ (может быть null)
     *
     * @return true, если элемент был удалён; false, если ключ не найден
     */
    public boolean remove(K key) {
        int id = hash(key);
        Pair<K, V> current = table[id];
        Pair<K, V> prev = null;
        while (current != null) {
            if (equalsKey(key, current.key)) {
                if (prev == null) {
                    table[id] = current.next;
                } else {
                    prev.next = current.next;
                }
                size--;
                modCount++;
                return true;
            }
            prev = current;
            current = current.next;
        }
        return false;
    }

    /**
     * Возвращает значение по ключу или null, если ключ не найден.
     *
     * @param key ключ (может быть null)
     *
     * @return значение или null
     */
    public V get(K key) {
        int id = hash(key);
        Pair<K, V> cur = table[id];
        while (cur != null) {
            if (equalsKey(key, cur.key)) {
                return cur.value;
            }
            cur = cur.next;
        }
        return null;
    }

    /**
     * Проверяет наличие ключа в таблице.
     *
     * @param key ключ (может быть null)
     *
     * @return true, если ключ присутствует
     */
    public boolean containsKey(K key) {
        int id = hash(key);
        Pair<K, V> cur = table[id];
        while (cur != null) {
            if (equalsKey(key, cur.key)) {
                return true;
            }
            cur = cur.next;
        }
        return false;
    }

    /**
     * Сравнивает таблицы на логическое равенство по множеству пар (k, v).
     * Выполняет симметричную проверку включения пар this и other.
     *
     * @param o другая таблица
     *
     * @return true, если таблицы логически равны
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof HashTable<?, ?> other)) return false;
        if (this.size != other.size) return false;
        for (int i = 0; i < table.length; i++) {
            Pair<K, V> current = table[i];
            while (current != null) {
                if (!hasEntry(other, current.key, current.value)) {
                    return false;
                }
                current = current.next;
            }
        }
        for (int i = 0; i < other.table.length; i++) {
            Pair<?, ?> current = other.table[i];
            while (current != null) {
                if (!hasEntry(this, current.key, current.value)) {
                    return false;
                }
                current = current.next;
            }
        }
        return true;
    }

    /**
     * Проверяет наличие пары (key, value) в указанной таблице.
     *
     * @param other другая таблица
     * @param key ключ
     * @param value значение
     *
     * @return true, если пара найдена
     */
    private static boolean hasEntry(HashTable<?, ?> other, Object key, Object value) {
        for (int i = 0; i < other.table.length; i++) {
            Pair<?, ?> cur = (Pair<?, ?>) other.table[i];
            while (cur != null) {
                if (java.util.Objects.equals(key, cur.key)) {
                    return java.util.Objects.equals(value, cur.value);
                }
                cur = cur.next;
            }
        }
        return false;
    }

    /**
     * Вычисляет хэш всей таблицы как сумму хэшей пар (k, v),
     * не зависящий от порядка обхода.
     *
     * @return хэш-код таблицы
     */
    @Override
    public int hashCode() {
        int h = 0;
        for (int i = 0; i < table.length; i++) {
            Pair<K, V> current = table[i];
            while (current != null) {
                h += java.util.Objects.hash(current.key, current.value);
                current = current.next;
            }
        }
        return h;
    }



    /**
     * Возвращает строковое представление вида {k1=v1, k2=v2}.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        boolean first = true;
        for (int i = 0; i < table.length; i++) {
            Pair<K, V> current = table[i];
            while (current != null) {
                if (!first) {
                    sb.append(", ");
                }
                sb.append(String.valueOf(current.key)).append("=");
                sb.append(String.valueOf(current.value));
                first = false;
                current = current.next;
            }
        }
        sb.append("}");
        return sb.toString();
    }

    /**
     * Возвращает fail-fast итератор по парам (k, v).
     */
    @Override
    public Iterator<Pair<K, V>> iterator() {
        return new HashTableIterator();
    }

    /**
     * Итератор по таблице: обходит пары в цепочках бакетов; fail-fast.
     */
    public class HashTableIterator implements Iterator<Pair<K, V>> {
        private int bucketIdx = 0;
        private Pair<K, V> current = null;
        private Pair<K, V> next = null;
        private final int expectedModCount;

        /**
         * Создаёт итератор и позиционируется на первом доступном элементе.
         */
        public HashTableIterator() {
            expectedModCount = modCount;
            advance();
        }

        /**
         * Продвигает указатель next к первому элементу следующего непустого бакета.
         */
        private void advance() {
            while (bucketIdx < table.length && (next = table[bucketIdx]) == null) {
                bucketIdx++;
            }
            if (bucketIdx < table.length) {
                current = null;
            }
        }

        /**
         * Есть ли следующий элемент.
         *
         * @return true, если следующий элемент существует
         */
        @Override
        public boolean hasNext() {
            if (expectedModCount != modCount) {
                throw new java.util.ConcurrentModificationException();
            }
            return next != null;
        }

        /**
         * Возвращает следующий элемент и продвигает итератор.
         *
         * @return следующая пара
         *
         * @throws java.util.ConcurrentModificationException при модификации коллекции
         * @throws java.util.NoSuchElementException если элементов больше нет
         */
        @Override
        public Pair<K, V> next() {
            if (expectedModCount != modCount) {
                throw new java.util.ConcurrentModificationException();
            }
            if (next == null) {
                throw new java.util.NoSuchElementException();
            }
            current = next;
            if (next.next != null) {
                next = next.next;
            } else {
                bucketIdx++;
                advance();
            }
            return current;
        }
    }
}
