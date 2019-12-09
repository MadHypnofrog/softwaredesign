package calculator.token;

import calculator.token.state.BracketState;
import calculator.token.state.NumberState;
import calculator.token.state.OperationState;
import calculator.token.state.StartState;
import calculator.token.state.State;

import java.util.ArrayList;
import java.util.List;

public class Tokenizer {

    private int index;
    private List<Token> tokens;
    private State state;
    private final String input;

    private Tokenizer(String input) {
        this.input = input;
        this.index = 0;
        this.state = new StartState(this);
        this.tokens = new ArrayList<>();
    }

    public static List<Token> parse(String input) {
        input += '\0';
        return new Tokenizer(input).parse();
    }

    private List<Token> parse() {
        while (isNotEndOfLine()) {
            char ch = input.charAt(index);

            if (Character.isWhitespace(ch)) {
                index++;
                continue;
            } else if (Character.isDigit(ch)) {
                state = new NumberState(this);
            } else if (isBracket(ch)) {
                state = new BracketState(this);
            } else if (isOperation(ch)) {
                state = new OperationState(this);
            }

            tokens.add(state.createToken());
        }

        return tokens;
    }

    private boolean isNotEndOfLine() {
        return index < input.length() && input.charAt(index) != '\0';
    }

    private static boolean isBracket(char ch) {
        return ch == '(' || ch == ')';
    }

    private static boolean isOperation(char ch) {
        return ch == '*' || ch == '/' || ch == '+' || ch == '-';
    }

    public char current() {
        if (index < input.length()) {
            return input.charAt(index);
        } else {
            throw new IndexOutOfBoundsException("Index out of bounds: " + index);
        }
    }

    public char next() {
        index++;
        return current();
    }
}