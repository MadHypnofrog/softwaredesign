package calculator.visitor;

import calculator.token.BracketToken;
import calculator.token.NumberToken;
import calculator.token.OperationToken;

public interface TokenVisitor {
    void visit(NumberToken token);

    void visit(BracketToken token);

    void visit(OperationToken token);
}
