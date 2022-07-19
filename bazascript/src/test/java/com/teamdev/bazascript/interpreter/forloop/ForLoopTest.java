package com.teamdev.bazascript.interpreter.forloop;

import com.teamdev.bazascript.interpreter.AbstractTest;
import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

import static org.junit.jupiter.params.provider.Arguments.of;

class ForLoopTest extends AbstractTest {

    static Stream<Arguments> positiveCases() {
        return Stream.of(
                of("for (a = 3; a > 0; a = a - 1;){print a;};", "[3.0][2.0][1.0]",
                        "Simple for loop test test has failed"),

                of("for (a = 3; a > 0; a = a - 1;){b = 4; c= 3;}; print(a, b, c) ;", "[0.0][4.0][3.0]",
                        "For loop with code after it test has failed")
        );

    }

    static Stream<Arguments> negativeCases() {
        return Stream.of(

                of("for(a = 3 > 0; a > 0; a = a - 1;){print a;};", 18,
                        "Boolean variable initialization inside for loop test has not throw exception"),

                of("for a = 3; a > 0; a = a - 1;){print a;};", 23,
                        "For loop without opening bracket test has not throw exception"),

                of("for(a = 3 a > 0; a = a - 1;){print a;};", 17,
                        "For loop condition without separator test has not throw exception"),

                of("for(a = 3 > 0; a > 0; a = a - 1;{print a;};", 25,
                        "For loop without closing bracket test has not throw exception"),

                of("for(a = 3 > 0; a > 0; a = a - 1;)", 17,
                        "For loop without body test has not throw exception"),

                of("for(a = 3 > 0; a > 0; a = a - 1;){print a;", 18,
                        "For loop without closing brace test has not throw exception"),

                of("for(a = 3 > 0; a > 0; a = a - 1;)print a;};", 13,
                        "For loop without opening brace test has not throw exception")
        );
    }
}
