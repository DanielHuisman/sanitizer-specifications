package io.danielhuisman.sanitizers.language;

import io.danielhuisman.sanitizers.generators.Generators;
import org.sat4j.specs.TimeoutException;

import java.io.IOException;

public class LanguageTest {

    public static void main(String[] args) {
        try {
            Generators.initialize();

            Language.runString(
                    "test1 = length (\"=\", 10)\nprint test1\ntest2 = word (\"equals\", \"abc\")\n" +
                    "accepts test2 \"abc\"\nrejects test2 \"def\""
            );

            Language.runFile("examples/test.san");
        } catch (ReflectiveOperationException | IOException | TimeoutException e) {
            e.printStackTrace();
        }
    }
}
