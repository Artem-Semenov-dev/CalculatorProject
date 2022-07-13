package com.teamdev.bazascript.interpreter.logicalexpression;

import com.teamdev.bazascript.interpreter.AbstractTest;
import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

import static org.junit.jupiter.params.provider.Arguments.of;

public class LogicalExpressionTest extends AbstractTest {

    static Stream<Arguments> positiveCases() {
        return Stream.of(
                of("a = 1 < 10; b = 20 > 100; d = a && b; print(d);", "[false]", "Simple conjunction test has failed."),

                of("a = 1 < 10; b = 20 > 100; c = 1 < 5; d = a && b || c; print(d);", "[true]", "Simple conjunction test has failed."),

                of("a = 1; b = 20; c = 10; d = b > 100 || c > 9; print(d);", "[true]", "Simple disjunction test has failed."),

                of("a = 1; b = 20; c = 10; print(b > 100 || c > 9);", "[true]", "Logical expression inside procedure test has failed."),

                of("a = 1; b = 20; c = 10; d = a < 10 && b > 100 || c > 10; print(d);", "[false]",
                        "Conjunction and disjunction test has failed."),

                of("a = 1; b = 20; c = 10; d = a < 10 && b > 100 || c <= 10 && b > 100 || c <= 10; print(d);", "[true]",
                        "Logical expression inside brackets test has failed")
        );
    }

    static Stream<Arguments> negativeCases() {
        return Stream.of(
                of("a = 1; b = 20; d = a < 10 && ;", 29, "Logical expression without right operand test has not throw exception"),

                of("a = 1; b = 20; d = && a < 10;", 19, "Logical expression without left operand test has not throw exception"),

                of("a = 1; b = 20; d = a + 5 && a < 10;", 25,
                        "Numeric expression as left operand of logical expression test has not throw exception"),

                of("a = 1; b = 20; d = a + 5 && a - 10;", 25,
                        "Logical operand between numeric expressions test has not throw exception"),

                of("a = 1; b = 20; d = a > 5 & & a < 10;", 25,
                        "Space inside logical operand test has not throw exception")

        );
    }
}
