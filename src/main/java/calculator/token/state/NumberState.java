package calculator.token.state;

import calculator.token.NumberToken;
import calculator.token.Token;
import calculator.token.Tokenizer;

public class NumberState extends State {

    public NumberState(Tokenizer tokenizer) {
        super(tokenizer);
    }

    @Override
    public Token createToken() {
        char currCh = tokenizer.current();
        StringBuilder builder = new StringBuilder();

        while (Character.isDigit(currCh)) {
            builder.append(currCh);
            currCh = tokenizer.next();
        }

        return new NumberToken(Long.parseLong(builder.toString()));
    }
}