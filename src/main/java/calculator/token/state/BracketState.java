package calculator.token.state;

import calculator.token.BracketToken;
import calculator.token.Token;
import calculator.token.TokenType;
import calculator.token.Tokenizer;

public class BracketState extends State {

    public BracketState(Tokenizer tokenizer) {
        super(tokenizer);
    }

    @Override
    public Token createToken() {
        char ch = tokenizer.current();
        tokenizer.next();
        return new BracketToken(parseType(ch));
    }

    private static TokenType parseType(char ch) {
        switch (ch) {
            case '(':
                return TokenType.LPAREN;
            case ')':
                return TokenType.RPAREN;
        }

        throw new IllegalArgumentException("Undefined operation '" + ch + "'");
    }
}