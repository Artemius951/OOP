// Card.java с enum
package ru.nsu.kutsenko.task112;

/**
 * Класс представляющий игральную карту.
 * Содержит масть, достоинство и значение карты.
 */
public class Card {
    public enum Suit {
        SPADES("Пики"), HEARTS("Червы"), DIAMONDS("Бубны"), CLUBS("Трефы");

        private final String name;

        Suit(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        @Override
        public String toString() {
            return name;
        }
    }

    public enum Rank {
        TWO("Двойка", 2), THREE("Тройка", 3), FOUR("Четверка", 4),
        FIVE("Пятерка", 5), SIX("Шестерка", 6), SEVEN("Семерка", 7),
        EIGHT("Восьмерка", 8), NINE("Девятка", 9), TEN("Десятка", 10),
        JACK("Валет", 10), QUEEN("Дама", 10), KING("Король", 10), ACE("Туз", 11);

        private final String name;
        private final int value;

        Rank(String name, int value) {
            this.name = name;
            this.value = value;
        }

        public String getName() {
            return name;
        }

        public int getValue() {
            return value;
        }

        @Override
        public String toString() {
            return name;
        }
    }

    private final Suit suit;
    private final Rank rank;

    /**
     * Конструктор карты.
     *
     * @param suit масть карты.
     * @param rank достоинство карты.
     */
    public Card(Suit suit, Rank rank) {
        this.suit = suit;
        this.rank = rank;
    }

    /**
     * Возвращает текстовое представление карты.
     *
     * @return строка с описанием карты.
     */
    public String textCard() {
        return rank.getName() + " " + suit.getName() + " (" + rank.getValue() + ")";
    }

    /**
     * Возвращает масть карты.
     *
     * @return масть карты.
     */
    public Suit getSuit() {
        return suit;
    }

    /**
     * Возвращает достоинство карты.
     *
     * @return достоинство карты.
     */
    public Rank getRank() {
        return rank;
    }

    /**
     * Возвращает числовое значение карты.
     *
     * @return значение карты.
     */
    public int getValue() {
        return rank.getValue();
    }

    /**
     * Строковое представление карты.
     *
     * @return текстовое описание карты.
     */
    @Override
    public String toString() {
        return textCard();
    }
}