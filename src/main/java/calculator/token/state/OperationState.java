package calculator.token.state;

import calculator.token.OperationToken;
import calculator.token.Token;
import calculator.token.TokenType;
import calculator.token.Tokenizer;

public class OperationState extends State {

    public OperationState(Tokenizer tokenizer) {
        super(tokenizer);
    }

    @Override
    public Token createToken() {
        char ch = tokenizer.current();
        tokenizer.next();
        return new OperationToken(parseType(ch));
    }

    private static TokenType parseType(char ch) {
        switch (ch) {
            case '+':
                return TokenType.PLUS;
            case '-':
                return TokenType.MINUS;
            case '*':
                return TokenType.MULT;
            case '/':
                return TokenType.DIV;
        }

        throw new IllegalArgumentException("Undefined operation '" + ch + "'");
    }
}