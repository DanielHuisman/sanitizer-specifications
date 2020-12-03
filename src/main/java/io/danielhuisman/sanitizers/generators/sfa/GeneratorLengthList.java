package io.danielhuisman.sanitizers.generators.sfa;

import io.danielhuisman.sanitizers.Util;
import io.danielhuisman.sanitizers.sfa.SFAWrapper;
import org.apache.commons.lang3.tuple.Pair;
import org.sat4j.specs.TimeoutException;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class GeneratorLengthList extends SFAGenerator<Collection<Collection<Pair<GeneratorLength.Operator, Integer>>>> {

    private final GeneratorLength generatorLength;

    public GeneratorLengthList() {
        generatorLength = new GeneratorLength();
    }

    @Override
    public String getName() {
        return "length-list";
    }

    @Override
    public SFAWrapper generate(Collection<Collection<Pair<GeneratorLength.Operator, Integer>>> input) throws TimeoutException {
        // Match all lengths if no input was provided
        if (input.size() == 0) {
            return generatorLength.generate(Pair.of(GeneratorLength.Operator.GREATER_THAN_OR_EQUALS, 0));
        }

        // Combine length inputs using union for the outer list and intersection for the inner list (sum of products)
        return SFAWrapper.union(
                input
                    .stream()
                    .map(Util.wrapper(inputAnd -> SFAWrapper.intersection(
                            inputAnd
                                    .stream()
                                    .map(Util.wrapper(generatorLength::generate))
                                    .collect(Collectors.toList())
                    )))
                    .collect(Collectors.toList())
        );
    }

    @Override
    public Collection<Pair<String, SFAWrapper>> generateExamples() throws TimeoutException {
        List<Pair<String, SFAWrapper>> examples = new LinkedList<>();

        examples.add(Pair.of("gte_3_lte_5", generate(List.of(
                List.of(
                        Pair.of(GeneratorLength.Operator.GREATER_THAN_OR_EQUALS, 3),
                        Pair.of(GeneratorLength.Operator.LESS_THAN_OR_EQUALS, 5)
                )
        ))));

        examples.add(Pair.of("gte_3_lte_5_or_gt_8", generate(List.of(
                List.of(
                        Pair.of(GeneratorLength.Operator.GREATER_THAN_OR_EQUALS, 3),
                        Pair.of(GeneratorLength.Operator.LESS_THAN_OR_EQUALS, 5)
                ),
                List.of(
                        Pair.of(GeneratorLength.Operator.GREATER_THAN, 8)
                )
        ))));

        return examples;
    }
}
