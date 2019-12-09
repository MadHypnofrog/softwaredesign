package calculator.token;

import calculator.visitor.TokenVisitor;

public class NumberToken implements Token {

    private final long value;

    public NumberToken(long value) {
        this.value = value;
    }

    @Override
    public void accept(TokenVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public TokenType getTokenType() {
        return TokenType.NUMBER;
    }

    public long getValue() {
        return value;
    }

    @Override
    public String toVisitorString() {
        return "NUMBER(" + Long.toString(value) + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof NumberToken)) return false;
        NumberToken that = (NumberToken) o;
        return value == that.value;
    }

}