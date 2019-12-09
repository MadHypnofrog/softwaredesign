package calculator.token;

import calculator.visitor.TokenVisitor;

public class OperationToken implements Token {

    private final TokenType type;

    public OperationToken(TokenType type) {
        this.type = type;
    }

    @Override
    public void accept(TokenVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public TokenType getTokenType() {
        return type;
    }

    @Override
    public String toVisitorString() {
        return type.toString();
    }

    public long evaluate(long a, long b) {
        switch (type) {
            case PLUS:
                return a + b;
            case MINUS:
                return a - b;
            case MULT:
                return a * b;
            case DIV:
                return a / b;
        }
        throw new RuntimeException();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OperationToken)) return false;
        OperationToken that = (OperationToken) o;
        return type == that.type;
    }

}
