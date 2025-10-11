package ru.nsu.kutsenko.task113;

/**
 * Абстрактный класс для представления бинарных операций.
 * Содержит левое и правое выражения и общую логику для бинарных операций.
 */
public abstract class BinaryOperation extends Expression {
    /** Левое выражение. */
    protected Expression left;
    /** Правое выражение. */
    protected Expression right;

    /**
     * Конструктор бинарной операции.
     *
     * @param left  левое выражение
     * @param right правое выражение
     */
    public BinaryOperation(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }

    /**
     * Абстрактный метод для получения строкового представления оператора.
     *
     * @return строковое представление оператора
     */
    protected abstract String getOperator();

    /**
     * Возвращает строковое представление бинарной операции
     * в формате (левый_операнд оператор правый_операнд).
     *
     * @return строковое представление операции
     */
    @Override
    public String toString() {
        return "(" + left.toString() + getOperator() + right.toString() + ")";
    }

    /**
     * Упрощает бинарную операцию: сначала упрощает операнды,
     * затем применяет специфичные для операции правила упрощения.
     *
     * @return упрощенное выражение
     */
    @Override
    public Expression simplify() {
        Expression simplifiedLeft = left.simplify();
        Expression simplifiedRight = right.simplify();
        return simplifySpecific(simplifiedLeft, simplifiedRight);
    }

    /**
     * Абстрактный метод для применения специфичных правил упрощения операции.
     *
     * @param left  упрощенное левое выражение
     * @param right упрощенное правое выражение
     * @return упрощенное выражение
     */
    protected abstract Expression simplifySpecific(Expression left, Expression right);
}