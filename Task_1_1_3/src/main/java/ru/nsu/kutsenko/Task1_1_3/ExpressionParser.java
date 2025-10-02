package ru.nsu.kutsenko.task113;

class ExpressionParser {
    private String input;
    private int pos;

    public Expression parse(String expression) {
        this.input = expression.replaceAll("\\s+", "");
        this.pos = 0;
        return parseExpression();
    }

    private Expression parseExpression() {
        if (pos >= input.length()) {
            throw new RuntimeException("Unexpected end of expression");
        }

        if (input.charAt(pos) == '(') {
            pos++;
            Expression left = parseExpression();

            char operator = input.charAt(pos);
            pos++;

            Expression right = parseExpression();

            if (input.charAt(pos) != ')') {
                throw new RuntimeException("Expected ')' at position " + pos);
            }
            pos++;

            switch (operator) {
                case '+': return new Add(left, right);
                case '-': return new Sub(left, right);
                case '*': return new Mul(left, right);
                case '/': return new Div(left, right);
                default: throw new RuntimeException("Unknown operator: " + operator);
            }
        } else {
            return parseAtom();
        }
    }

    private Expression parseAtom() {
        StringBuilder sb = new StringBuilder();

        while (pos < input.length() &&
            (Character.isDigit(input.charAt(pos)) ||
                Character.isLetter(input.charAt(pos)))) {
            sb.append(input.charAt(pos));
            pos++;
        }

        String atom = sb.toString();
        if (atom.isEmpty()) {
            throw new RuntimeException("Expected number or variable at position " + pos);
        }

        try {
            int value = Integer.parseInt(atom);
            return new Number(value);
        } catch (NumberFormatException e) {
            return new Variable(atom);
        }
    }
}