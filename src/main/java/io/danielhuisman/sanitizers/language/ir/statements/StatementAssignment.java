package io.danielhuisman.sanitizers.language.ir.statements;

import io.danielhuisman.sanitizers.util.Util;
import io.danielhuisman.sanitizers.language.ir.Identifier;
import io.danielhuisman.sanitizers.language.ir.Memory;
import io.danielhuisman.sanitizers.language.ir.expressions.Expression;
import org.antlr.v4.runtime.Token;
import org.sat4j.specs.TimeoutException;

public class StatementAssignment extends Statement {

    public Identifier identifier;
    public Expression expression;

    public StatementAssignment(Token start, Token end, Identifier identifier, Expression expression) {
        super(start, end);
        this.identifier = identifier;
        this.expression = expression;
    }

    @Override
    public Void execute(Memory memory) throws TimeoutException {
        if (memory.has(identifier)) {
            throw new RuntimeException(String.format("Identifier \"%s\" is already defined.", identifier.getName()));
        }

        memory.set(identifier, expression.execute(memory));

        return null;
    }

    @Override
    public String toString() {
        return "assignment\n" +
                Util.indent(identifier.toString()) + "\n" +
                Util.indent(expression.toString());
    }
}
