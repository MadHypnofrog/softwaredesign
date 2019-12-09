package calculator.visitor;

import calculator.token.BracketToken;
import calculator.token.NumberToken;
import calculator.token.OperationToken;
import calculator.token.Token;
import calculator.token.TokenType;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

public class ParserVisitor implements TokenVisitor {

    private final Deque<Token> tokens;
    private final Deque<Token> stack;

    private ParserVisitor() {
        tokens = new ArrayDeque<>();
        stack = new ArrayDeque<>();
    }

    public static List<Token> convertToReversePolishNotation(List<Token> tokens) {
        return new ParserVisitor().convert(tokens);
    }

    private List<Token> convert(List<Token> input) {
        input.forEach(token -> token.accept(this));

        while (!stack.isEmpty()) {
            this.tokens.add(stack.pollLast());
        }

        return new ArrayList<>(this.tokens);
    }


    @Override
    public void visit(NumberToken token) {
        tokens.add(token);
    }

    @Override
    public void visit(BracketToken token) {
        TokenType type = token.getTokenType();
        if (type == TokenType.LPAREN) {
            stack.add(token);
        } else {
            Token currToken = stack.pollLast();
            while (!stack.isEmpty() && currToken.getTokenType() != TokenType.LPAREN) {
                tokens.add(currToken);
                currToken = stack.pollLast();
            }
        }
    }

    @Override
    public void visit(OperationToken token) {
        if (stack.isEmpty()) {
            stack.add(token);
        } else {
            TokenType currType = token.getTokenType();
            Token lastToken = stack.peekLast();
            while (!stack.isEmpty() && currType.getPriority() <= lastToken.getTokenType().getPriority()) {
                tokens.add(stack.pollLast());
                lastToken = stack.peekLast();
            }
            stack.add(token);
        }
    }
}
