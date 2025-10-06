package ru.nsu.kutsenko.task113;

import java.util.ArrayList;
import java.util.List;

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
            throw new ExpressionParseException(
                "Unexpected characters at position " + result.position);
        }

        return result.expression;
    }

    /**
     * Парсит строку с математическим выражением без обязательных скобок.
     * Поддерживает стандартные приоритеты операций.
     *
     * @param expression строка с выражением
     * @return объект Expression, представляющий распарсенное выражение
     */
    public static Expression parseWithoutParentheses(String expression) {
        String cleanedInput = expression.replaceAll("\\s+", "");
        return parseExpressionWithoutParentheses(cleanedInput);
    }

    /**
     * Парсит выражение без скобок с учетом приоритетов операций.
     */
    private static Expression parseExpressionWithoutParentheses(String input) {
        List<Object> tokens = tokenize(input);
        return parseExpressionFromTokens(tokens, 0, tokens.size() - 1);
    }

    /**
     * Разбивает строку на токены: числа, переменные и операторы.
     */
    private static List<Object> tokenize(String input) {
        List<Object> tokens = new ArrayList<>();
        int pos = 0;

        while (pos < input.length()) {
            char c = input.charAt(pos);

            if (Character.isDigit(c)) {
                StringBuilder sb = new StringBuilder();
                while (pos < input.length() && Character.isDigit(input.charAt(pos))) {
                    sb.append(input.charAt(pos));
                    pos++;
                }
                tokens.add(new Number(Integer.parseInt(sb.toString())));
            } else if (Character.isLetter(c)) {
                StringBuilder sb = new StringBuilder();
                while (pos < input.length() && Character.isLetterOrDigit(input.charAt(pos))) {
                    sb.append(input.charAt(pos));
                    pos++;
                }
                tokens.add(new Variable(sb.toString()));
            } else if (isOperator(c)) {
                tokens.add(Character.valueOf(c));
                pos++;
            } else {
                throw new ExpressionParseException("Unexpected character: '" + c + "' at position "
                    + pos);
            }
        }

        return tokens;
    }

    /**
     * Парсит выражение из списка токенов с учетом приоритетов.
     */
    private static Expression parseExpressionFromTokens(List<Object> tokens, int start, int end) {
        if (start > end) {
            throw new ExpressionParseException("Empty expression");
        }
        for (int i = end; i >= start; i--) {
            Object token = tokens.get(i);
            if (token instanceof Character) {
                char op = (Character) token;
                if (op == '+' || op == '-') {
                    Expression left = parseExpressionFromTokens(tokens, start, i - 1);
                    Expression right = parseExpressionFromTokens(tokens, i + 1, end);
                    return createOperation(left, right, op);
                }
            }
        }
        for (int i = end; i >= start; i--) {
            Object token = tokens.get(i);
            if (token instanceof Character) {
                char op = (Character) token;
                if (op == '*' || op == '/') {
                    Expression left = parseExpressionFromTokens(tokens, start, i - 1);
                    Expression right = parseExpressionFromTokens(tokens, i + 1, end);
                    return createOperation(left, right, op);
                }
            }
        }

        if (start == end) {
            Object token = tokens.get(start);
            if (token instanceof Expression) {
                return (Expression) token;
            } else {
                throw new ExpressionParseException("Expected expression, found: " + token);
            }
        }

        throw new ExpressionParseException("Invalid expression structure");
    }

    /**
     * Создает операцию на основе оператора.
     */
    private static Expression createOperation(Expression left, Expression right, char op) {
        switch (op) {
            case '+': return new Add(left, right);
            case '-': return new Sub(left, right);
            case '*': return new Mul(left, right);
            case '/': return new Div(left, right);
            default: throw new UnknownOperatorException(op);
        }
    }

    /**
     * Проверяет, является ли символ оператором.
     */
    private static boolean isOperator(char c) {
        return c == '+' || c == '-' || c == '*' || c == '/';
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
                throw new ExpressionParseException(
                    "Unexpected end of expression after left operand");
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
                case '+':
                    operation = new Add(left, right);
                    break;
                case '-':
                    operation = new Sub(left, right);
                    break;
                case '*':
                    operation = new Mul(left, right);
                    break;
                case '/':
                    operation = new Div(left, right);
                    break;
                default:
                    throw new UnknownOperatorException(operator);
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
     * Вспомогательный класс для возврата результата парсинга.
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