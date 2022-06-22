package com.teamdev.bazascript.interpreter;

import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

import static org.junit.jupiter.params.provider.Arguments.of;

class InterpreterTest extends AbstractTest {
    static Stream<Arguments> positiveSource() {
        return Stream.of(
                of("a = 5; println(a);", "[5.0]", "Evaluation of initialization and print failed"),
                of("a = 5;b = 4;println(a + b); println(a)", "[9.0][5.0]", "Evaluation of initialization two variables and sum of them failed"),
                of("a = 5; b = 3 + 7; println(a*b);", "[50.0]", "Evaluation of initialization two variables and multiply of them failed"),
                of("a = max(3,min(5,10)); b = 3 + 7; println(a*b);", "[50.0]", "Execution of code with math functions has failed"),
                of("a=5;b=0+7;c=7-2;println(5*(b+3)+c);", "[55.0]",
                        "Evaluation of initialization 3 variables and multiply/sum of them failed"));
    }
}

