package ru.nsu.kutsenko.task112;

/**
 * Класс представляющий игральную карту
 * Содержит масть, достоинство и значение карты
 */
public class Card {
    private final String suit;
    private final String rank;
    private final int value;

    /**
     * Конструктор карты
     * @param suit масть карты
     * @param rank достоинство карты
     * @param value числовое значение карты
     */
    public Card(String suit, String rank, int value) {
        this.suit = suit;
        this.rank = rank;
        this.value = value;
    }

    /**
     * Возвращает текстовое представление карты
     * @return строка с описанием карты
     */
    public String textCard() {
        return rank + " " + suit + " (" + value + ")";
    }

    /**
     * Возвращает масть карты
     * @return масть карты
     */
    public String getSuit() {
        return suit;
    }

    /**
     * Возвращает достоинство карты
     * @return достоинство карты
     */
    public String getRank() {
        return rank;
    }

    /**
     * Возвращает числовое значение карты
     * @return значение карты
     */
    public int getValue() {
        return value;
    }

    /**
     * Строковое представление карты
     * @return текстовое описание карты
     */
    @Override
    public String toString() {
        return textCard();
    }
}