package ru.nsu.kutsenko.Task1_1_3;

/**
 * Парсер математических выражений.
 * Преобразует строковое представление выражения в древовидную структуру.
 */
public class ExpressionParser {
    private String input;
    private int pos;

    /**
     * Парсит строку с математическим выражением.
     *
     * @param expression строка с выражением
     * @return объект Expression, представляющий распарсенное выражение
     */
    public Expression parse(String expression) {
        this.input = expression.replaceAll("\\s+", "");
        this.pos = 0;
        return parseExpression();
    }

    /**
     * Парсит выражение, начиная с текущей позиции.
     * Обрабатывает бинарные операции и выражения в скобках.
     *
     * @return распарсенное выражение
     * @throws ExpressionParseException если достигнут конец выражения или отсутствует закрывающая скобка
     */
    private Expression parseExpression() {
        if (pos >= input.length()) {
            throw new ExpressionParseException("Unexpected end of expression");
        }

        if (input.charAt(pos) == '(') {
            pos++;
            Expression left = parseExpression();

            final char operator = input.charAt(pos);
            pos++;

            Expression right = parseExpression();

            if (input.charAt(pos) != ')') {
                throw new ExpressionParseException("Expected ')' at position " + pos);
            }
            pos++;

            switch (operator) {
                case '+': return new Add(left, right);
                case '-': return new Sub(left, right);
                case '*': return new Mul(left, right);
                case '/': return new Div(left, right);
                default: throw new UnknownOperatorException(operator);
            }
        } else {
            return parseAtom();
        }
    }

    /**
     * Парсит атомарные элементы выражения (числа или переменные).
     *
     * @return Number для чисел или Variable для переменных
     * @throws ExpressionParseException если атом пустой
     */
    private Expression parseAtom() {
        StringBuilder sb = new StringBuilder();

        while (pos < input.length()
            && (Character.isDigit(input.charAt(pos))
            || Character.isLetter(input.charAt(pos)))) {
            sb.append(input.charAt(pos));
            pos++;
        }

        String atom = sb.toString();
        if (atom.isEmpty()) {
            throw new ExpressionParseException("Expected number or variable at position " + pos);
        }

        try {
            int value = Integer.parseInt(atom);
            return new Number(value);
        } catch (NumberFormatException e) {
            return new Variable(atom);
        }
    }
}