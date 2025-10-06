package ru.nsu.kutsenko.task113;

import java.util.HashMap;
import java.util.Map;

/**
 * Класс для упрощения математических выражений.
 */
public class ExpressionSimplifier {

    private ExpressionSimplifier() {}

    /**
     * Упрощает выражение по заданным правилам.
     * Не изменяет исходное выражение, возвращает новое упрощенное.
     *
     * @param expression выражение для упрощения
     * @return упрощенное выражение
     */
    public static Expression simplify(Expression expression) {
        if (expression instanceof Number) {
            return expression;
        } else if (expression instanceof Variable) {
            return expression;
        } else if (expression instanceof BinaryOperation) {
            return simplifyBinaryOperation((BinaryOperation) expression);
        } else {
            return expression;
        }
    }

    /**
     * Упрощает бинарную операцию.
     */
    private static Expression simplifyBinaryOperation(BinaryOperation operation) {
        Expression left = simplify(operation.left);
        Expression right = simplify(operation.right);

        if (canEvaluateWithoutVariables(left, right)) {
            try {
                Map<String, Integer> emptyVars = new HashMap<>();
                int result = operation.eval(emptyVars);
                return new Number(result);
            } catch (ExpressionEvaluationException e) {
                // Игнорируем исключение - продолжаем упрощение другими методами
                // Был исправлен пустой блок catch
            }
        }

        if (operation instanceof Add) {
            return simplifyAdd((Add) operation, left, right);
        } else if (operation instanceof Sub) {
            return simplifySub((Sub) operation, left, right);
        } else if (operation instanceof Mul) {
            return simplifyMul((Mul) operation, left, right);
        } else if (operation instanceof Div) {
            return simplifyDiv((Div) operation, left, right);
        }

        return recreateOperation(operation, left, right);
    }

    /**
     * Проверяет, можно ли вычислить выражение без переменных.
     */
    private static boolean canEvaluateWithoutVariables(Expression left, Expression right) {
        return !containsVariables(left) && !containsVariables(right);
    }

    /**
     * Проверяет, содержит ли выражение переменные.
     */
    private static boolean containsVariables(Expression expression) {
        if (expression instanceof Variable) {
            return true;
        } else if (expression instanceof Number) {
            return false;
        } else if (expression instanceof BinaryOperation) {
            BinaryOperation op = (BinaryOperation) expression;
            return containsVariables(op.left) || containsVariables(op.right);
        }
        return true;
    }

    /**
     * Упрощает операцию сложения.
     */
    private static Expression simplifyAdd(Add add, Expression left, Expression right) {
        if (isZero(left)) {
            return right;
        }
        if (isZero(right)) {
            return left;
        }
        return new Add(left, right);
    }

    /**
     * Упрощает операцию вычитания.
     */
    private static Expression simplifySub(Sub sub, Expression left, Expression right) {
        if (left.toString().equals(right.toString())) {
            return new Number(0);
        }
        if (isZero(right)) {
            return left;
        }
        return new Sub(left, right);
    }

    /**
     * Упрощает операцию умножения.
     */
    private static Expression simplifyMul(Mul mul, Expression left, Expression right) {
        if (isZero(left) || isZero(right)) {
            return new Number(0);
        }
        if (isOne(left)) {
            return right;
        }
        if (isOne(right)) {
            return left;
        }
        return new Mul(left, right);
    }

    /**
     * Упрощает операцию деления.
     */
    private static Expression simplifyDiv(Div div, Expression left, Expression right) {
        if (isZero(left) && !isZero(right)) {
            return new Number(0);
        }
        if (isOne(right)) {
            return left;
        }
        if (left.toString().equals(right.toString()) && !isZero(left)) {
            return new Number(1);
        }
        return new Div(left, right);
    }

    /**
     * Проверяет, является ли выражение нулем.
     */
    private static boolean isZero(Expression expression) {
        return expression instanceof Number && ((Number) expression).getValue() == 0;
    }

    /**
     * Проверяет, является ли выражение единицей.
     */
    private static boolean isOne(Expression expression) {
        return expression instanceof Number && ((Number) expression).getValue() == 1;
    }

    /**
     * Создает новую операцию того же типа с упрощенными операндами.
     */
    private static Expression recreateOperation(BinaryOperation operation,
                                                Expression left, Expression right) {
        if (operation instanceof Add) {
            return new Add(left, right);
        } else if (operation instanceof Sub) {
            return new Sub(left, right);
        } else if (operation instanceof Mul) {
            return new Mul(left, right);
        } else if (operation instanceof Div) {
            return new Div(left, right);
        }
        return operation;
    }
}