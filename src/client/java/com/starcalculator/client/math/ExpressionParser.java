package com.starcalculator.client.math;

public class ExpressionParser {

    private String input;
    private int pos;

    public double evaluate(String expression) {
        input = expression.replace(" ", "");
        pos = 0;

        double value = parseExpression();

        if (pos != input.length()) {
            throw new RuntimeException("Unexpected character");
        }

        return value;
    }

    private double parseExpression() {
        double value = parseTerm();

        while (true) {
            if (match('+')) {
                value += parseTerm();
            } else if (match('-')) {
                value -= parseTerm();
            } else {
                break;
            }
        }

        return value;
    }

    private double parseTerm() {
        double value = parseFactor();

        while (true) {
            if (match('*')) {
                value *= parseFactor();
            } else if (match('/')) {
                value /= parseFactor();
            } else {
                break;
            }
        }

        return value;
    }

    private double parseFactor() {
        if (match('(')) {
            double value = parseExpression();

            if (!match(')')) {
                throw new RuntimeException("Missing )");
            }

            return value;
        }

        return parseNumber();
    }

    private double parseNumber() {
        int start = pos;

        while (Character.isDigit(peek()) || peek() == '.') {
            pos++;
        }

        if (start == pos) {
            throw new RuntimeException("Expected number");
        }

        return Double.parseDouble(input.substring(start, pos));
    }

    private char peek() {
        if (pos >= input.length()) {
            return '\0';
        }

        return input.charAt(pos);
    }

    private boolean match(char c) {
        if (peek() == c) {
            pos++;
            return true;
        }

        return false;
    }
}