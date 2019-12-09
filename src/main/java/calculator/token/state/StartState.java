package calculator.token.state;

import calculator.token.Token;
import calculator.token.Tokenizer;

public class StartState extends State {

    public StartState(Tokenizer tokenizer) {
        super(tokenizer);
    }

    @Override
    public Token createToken() {
        throw new UnsupportedOperationException();
    }
}