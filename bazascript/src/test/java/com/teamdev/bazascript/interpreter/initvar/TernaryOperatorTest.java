package com.teamdev.bazascript.interpreter.initvar;

import com.teamdev.bazascript.interpreter.AbstractTest;
import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

import static org.junit.jupiter.params.provider.Arguments.of;

public class TernaryOperatorTest extends AbstractTest {

    static Stream<Arguments> positiveCases() {
        return Stream.of(
                of("a = 0; b = (a < 2) ? 3 : 8; print(b);", "[3.0]",
                        "Simple ternary operator test has failed"),

                of("a = 1; b = (a > 2) ? 5 : (a > 0) ? 5 : 0; print(b);", "[5.0]",
                        "Chain of two ternary operators test has failed"),

                of("a = 3; b = (a > 9) ? 5 : (a > 5) ? 2 : (a>=3) ? 2 : 5; print(b);", "[2.0]",
                        "Chain of three ternary operators test has failed"));
    }

    static Stream<Arguments> negativeCases() {
        return Stream.of(
                of("a = 6; b = (a < 2 ? 3 : 8; print(b);", 14,
                        "Relational expression inside ternary operator without closing bracket test has not throw exception"),
                of("a = 6; b = a < 2) ? 3 : 8; print(b);", 5,
                        "Relational expression inside ternary operator without opening bracket test has not throw exception"),
                of("a = 6; b = (a < 2)  3 : 8; print(b);", 20,
                        "Ternary operator without symbol ? test has not throw exception"),
                of("a = 6; b = (a < 2) ? 3; print(b);", 20,
                        "Ternary operator without if condition false expression test has not throw exception")
        );
    }
}
