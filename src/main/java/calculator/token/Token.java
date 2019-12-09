package calculator.token;

import calculator.visitor.TokenVisitor;

public interface Token {

    void accept(TokenVisitor visitor);

    TokenType getTokenType();

    String toVisitorString();
}