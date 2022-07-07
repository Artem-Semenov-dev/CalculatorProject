package com.teamdev.bazascript.interpreter.initvar;

import com.teamdev.bazascript.interpreter.AbstractTest;
import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

import static org.junit.jupiter.params.provider.Arguments.of;

public class TernaryOperatorTest extends AbstractTest {

    static Stream<Arguments> positiveCases() {
        return Stream.of(
                of("a = 0; b = a < 2 ? 3 : 8; print(b);", "[3.0]",
                        "Simple ternary operator test has failed"),

                of("a = 0; print(a < 2 ? 3 : 8);", "[3.0]",
                        "Ternary operator inside procedure test has failed"),

                of("a = 0; b = a < 2 ? 3<2 : 2<8; print(b);", "[false]",
                        "Simple ternary operator test with boolean value initialization has failed"),

                of("a = 1; b = a > 2 ? 5 : a > 0 ? 5+2*(3+5) : 0; print(b);", "[21.0]",
                        "Chain of two ternary operators test has failed"),

                of("a = 1; b = a > 2 ? 5 : a > 0 ? min(2,5) : 0; print(b);", "[2.0]",
                        "Chain of two ternary operators with function inside expression test has failed"),

                of("a = 3; b = a > 2 ? a > 1 ? 2 : 5; print(b);", "[2.0]",
                        "Chain of two ternary operators with true condition test has failed"),

                of("a = 3; b = a > 1 ? a > 2 ? a >=3 ? 2 : 5; print(b);", "[2.0]",
                        "Chain of three ternary operators with true condition test has failed"),

                of("a = 3; b = a > 9 ? 5 : a > 5 ? 2 : a>=3 ? 2 : 5; print(b);", "[2.0]",
                        "Chain of three ternary operators with false condition test has failed"));

    }

    static Stream<Arguments> negativeCases() {
        return Stream.of(

                of("a = 6; b = a < 2  3 : 8; print(b);", 18,
                        "Ternary operator without symbol ? test has not throw exception"),

                of("a = 6; b = a < 2 ? 3 : ; print(b);", 23,
                        "Ternary operator without expression if condition false test has not throw exception"),

                of("a = 6; b = a < 2 : 4; print(b);", 17,
                        "Ternary operator without expression if condition true test has not throw exception"),

                of("a = 6; b = a < 2 ? 3 : 4 print(b);", 25,
                        "Ternary operator without separator ; test has not throw exception"),

                of("a = 6; b = a + 2 ? 3 : 4; print(b);", 17,
                        "Ternary operator with numeric expression instead of relational test has not throw exception"),

                of("a = 6; b = a > 2 ?? 3 : 4; print(b);", 18,
                        "Ternary operator with  test has not throw exception"),

                of("a = 6; b = a ? 3 : 4; print(b);", 13,
                        "Ternary operator with double type instead of relational expression test has not throw exception"),

                of("a = 6; b =  ? 3 : 4; print(b);", 12,
                        "Ternary operator with missing relational expression test has not throw exception"),

                of("a = 6; b = a < 2 ? 3  4; print(b);", 22,
                        "Ternary operator with missing : test has not throw exception")
        );
    }
}
