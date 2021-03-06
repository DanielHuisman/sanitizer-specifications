package io.danielhuisman.sanitizers.util;

import theory.characters.CharConstant;
import theory.characters.CharFunc;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Util {

    public static String indent(String s) {
        return s.replaceAll("(?m)^", "    ");
    }

    public static List<Character> toCharList(String s) {
        return s.chars().mapToObj((c) -> (char) c).collect(Collectors.toList());
    }

    public static List<CharFunc> toCharFuncList(String s) {
        return s.chars().mapToObj((c) -> new CharConstant((char) c)).collect(Collectors.toList());
    }

    public static String charRangeAsString(char start, char end) {
        return IntStream.rangeClosed(start, end).mapToObj((i) -> "" + (char) i).collect(Collectors.joining());
    }

    @FunctionalInterface
    public interface FunctionWithException<T, R, E extends Exception> {

        R apply(T t) throws E;
    }

    public static <T, R, E extends Exception> Function<T, R> wrapper(FunctionWithException<T, R, E> fe) {
        return arg -> {
            try {
                return fe.apply(arg);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        };
    }
}
