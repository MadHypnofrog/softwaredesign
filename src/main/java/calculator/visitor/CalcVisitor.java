package calculator.visitor;

import calculator.token.BracketToken;
import calculator.token.NumberToken;
import calculator.token.OperationToken;
import calculator.token.Token;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;

public class CalcVisitor implements TokenVisitor {

    private final Deque<Long> stack;

    public CalcVisitor() {
        this.stack = new ArrayDeque<>();
    }

    public long calc(List<Token> tokens) {
        if (tokens.isEmpty()) {
            return 0L;
        }

        tokens.forEach(token -> token.accept(this));
        long result = stack.pollLast();

        if (!stack.isEmpty()) {
            throw new RuntimeException("Invalid expression");
        }

        return result;
    }

    @Override
    public void visit(NumberToken token) {
        stack.add(token.getValue());
    }

    @Override
    public void visit(BracketToken token) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void visit(OperationToken token) {
        if (stack.size() < 2) {
            throw new IllegalArgumentException();
        }

        final long b = stack.pollLast();
        final long a = stack.pollLast();
        stack.add(token.evaluate(a, b));
    }
}