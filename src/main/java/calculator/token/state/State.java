package calculator.token.state;

import calculator.token.Token;
import calculator.token.Tokenizer;

public abstract class State {

    protected final Tokenizer tokenizer;

    public State(Tokenizer tokenizer) {
        this.tokenizer = tokenizer;
    }

    public abstract Token createToken();
}