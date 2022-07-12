package com.teamdev.bazascript.interpreter;

import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

import static org.junit.jupiter.params.provider.Arguments.of;

class InterpreterTest extends AbstractTest {
    static Stream<Arguments> positiveCases() {
        return Stream.of(
                of("print(5<=2);", "[false]", "Boolean expression inside procedure test has failed"),
                of("a = 7; print(a<=2);", "[false]", "Boolean expression inside procedure test has failed"),
                of("a=(2*(7+3/(2+3)^4)); print(a);", "[14.0096]", "Variable initialization with complex expression " +
                        "and procedure print test has failed."),
                of("a = 0; while(a<4){a = a+1; print(a);};", "[1.0][2.0][3.0][4.0]",
                        "While loop test has failed"),
                of("a = 2; while(a<4){a = a+1;}; a = 7; b = 7 < 2; print(a); print(b);", "[7.0][false]",
                        "While loop test with code after loop test has failed"),
                of("a = 2; b = 7; while(a<4){a = a+1; while(b<10){b = b + 1;}}; print(a); print(b);", "[4.0][10.0]",
                        "Nested while loop test with code after loop has failed"),
                of("a = 5 > 2; print(5>2);", "[true]", "Boolean variable initialization test has failed"),
                of("a=10;print(a);", "[10.0]", "Variable initialization and procedure print test has failed."),
                of("a = 5;b = 4; print(a + b); print(a)", "[9.0][5.0]", "Two variables initialization and simple " +
                        "sum action inside print procedure test has failed."),
                of("a = 5; b = 7 + 7; print(a*b);", "[70.0]", "Two variables initialization and multiplication of them test has failed"),
                of("a = max(3,min(5,10)); b = 3 + 7; print(a*b);", "[50.0]", "Execution of code with nesting math functions has failed"),
                of("a=-7; b=4+7; c=7*2 ;print(5*(b+3)+c);", "[84.0]",
                        "3 variables initialization and calculation of them test has failed"));
    }

    static Stream<Arguments> negativeCases() {
        return Stream.of(
                of("print(a);", 7, "Not initialized variable test has not throw exception"),
                of("a = a; print(a);", 5, "Wrong initialization of variable test has not throw exception"),
                of("a = 6; print(a;", 14, "Not closed brackets inside procedure test has not throw exception"),
                of("a = 6; printa);", 13, "Not opened brackets inside procedure test has not throw exception"),
                of("a = 6 print(a);", 6, "Code without separators test has not throw exception"),
                of("a; = 6 print(a);", 1, "Separator in wrong place test has not throw exception"),
                of("a == 6; print(a);", 3, "Not allowed double assign test has not throw exception"),
                of("a = 6; 7; print(a);", 7, "Number without variable test has not throw exception"),
                of("a = 6; clear(); print(a);", 23, "Clear procedure test has not throw exception")
        );
    }
}

