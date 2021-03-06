package io.danielhuisman.sanitizers.language.ir.statements;

import io.danielhuisman.sanitizers.language.ir.Identifier;
import io.danielhuisman.sanitizers.language.ir.Memory;
import org.antlr.v4.runtime.Token;

public class StatementImport extends Statement {

    public Identifier identifier;

    public StatementImport(Token start, Token end, Identifier identifier) {
        super(start, end);
        this.identifier = identifier;
    }

    @Override
    public Void execute(Memory memory) {
        if (memory.has(identifier)) {
            throw new RuntimeException(String.format("Identifier \"%s\" already exists", identifier.getName()));
        }

//        var automaton = memory.get(identifier);
//
//        try {
//            automaton.createDotFile(identifier.getName(), "examples/");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        // TODO: read automaton from file and store it in memory

        return null;
    }

    @Override
    public String toString() {
        return "import " + identifier.toString();
    }
}
