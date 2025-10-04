package ru.nsu.kutsenko.task113;

/**
 * Парсер математических выражений.
 * Преобразует строковое представление выражения в древовидную структуру.
 */
public class ExpressionParser {

    // Приватный конструктор - нельзя создавать экземпляры
    private ExpressionParser() {}

    /**
     * Парсит строку с математическим выражением.
     *
     * @param expression строка с выражением
     * @return объект Expression, представляющий распарсенное выражение
     */
    public static Expression parse(String expression) {
        String cleanedInput = expression.replaceAll("\\s+", "");
        ParseResult result = parseExpression(cleanedInput, 0);

        if (result.position < cleanedInput.length()) {
            throw new ExpressionParseException("Unexpected characters at position " + result.position);
        }

        return result.expression;
    }

    /**
     * Парсит выражение, начиная с текущей позиции.
     * Обрабатывает бинарные операции и выражения в скобках.
     *
     * @return распарсенное выражение
     * @throws ExpressionParseException если достигнут конец выражения
     *         или отсутствует закрывающая скобка
     */
    private static ParseResult parseExpression(String input, int pos) {
        if (pos >= input.length()) {
            throw new ExpressionParseException("Unexpected end of expression");
        }

        if (input.charAt(pos) == '(') {
            pos++;
            ParseResult leftResult = parseExpression(input, pos);
            Expression left = leftResult.expression;
            pos = leftResult.position;

            if (pos >= input.length()) {
                throw new ExpressionParseException("Unexpected end of expression after left operand");
            }

            final char operator = input.charAt(pos);
            pos++;

            ParseResult rightResult = parseExpression(input, pos);
            Expression right = rightResult.expression;
            pos = rightResult.position;

            if (pos >= input.length() || input.charAt(pos) != ')') {
                throw new ExpressionParseException("Expected ')' at position " + pos);
            }
            pos++;

            Expression operation;
            switch (operator) {
                case '+': operation = new Add(left, right); break;
                case '-': operation = new Sub(left, right); break;
                case '*': operation = new Mul(left, right); break;
                case '/': operation = new Div(left, right); break;
                default: throw new UnknownOperatorException(operator);
            }

            return new ParseResult(operation, pos);
        } else {
            return parseAtom(input, pos);
        }
    }

    /**
     * Парсит атомарные элементы выражения (числа или переменные).
     *
     * @return Number для чисел или Variable для переменных
     * @throws ExpressionParseException если атом пустой
     */
    private static ParseResult parseAtom(String input, int pos) {
        StringBuilder sb = new StringBuilder();

        while (pos < input.length()
            && (Character.isDigit(input.charAt(pos))
            || Character.isLetter(input.charAt(pos)))) {
            sb.append(input.charAt(pos));
            pos++;
        }

        String atom = sb.toString();
        if (atom.isEmpty()) {
            throw new ExpressionParseException(
                "Expected number or variable at position " + pos);
        }

        try {
            int value = Integer.parseInt(atom);
            return new ParseResult(new Number(value), pos);
        } catch (NumberFormatException e) {
            return new ParseResult(new Variable(atom), pos);
        }
    }

    /**
     * Вспомогательный класс для возврата результата парсинга
     */
    private static class ParseResult {
        final Expression expression;
        final int position;

        ParseResult(Expression expression, int position) {
            this.expression = expression;
            this.position = position;
        }
    }
}