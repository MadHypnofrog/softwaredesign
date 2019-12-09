package calculator.token;

public enum TokenType {
    LPAREN,
    RPAREN,
    PLUS,
    MINUS,
    DIV,
    MULT,
    NUMBER;

    public int getPriority() {
        if (this == LPAREN || this == RPAREN) {
            return 0;
        } else if (this == PLUS || this == MINUS) {
            return 1;
        } else if (this == DIV || this == MULT) {
            return 2;
        } else {
            return Integer.MAX_VALUE;
        }
    }
}