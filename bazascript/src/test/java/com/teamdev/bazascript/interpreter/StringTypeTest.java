package com.teamdev.bazascript.interpreter;

import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

import static org.junit.jupiter.params.provider.Arguments.of;

class StringTypeTest extends AbstractTest{

    static Stream<Arguments> positiveCases() {
        return Stream.of(
                of("a = 'Hello World!'; print(a);", "[Hello World!]",
                        "Simple string type test has failed"),

                of("firstName = 'John'; lastName = 'Doe'; fullName = firstName + ' ' + lastName; print(fullName);",
                        "[John Doe]",
                        "String concatenation test has failed"),

                of("a = 'bytes'; b = 256 + ' ' + a ; print(b);",
                        "[256 bytes]",
                        "String concatenation with number test has failed"),

                of("a = 0; b = a < 2 ? 'One' : 'Two'; print(b);", "[One]",
                        "String type inside ternary operator test has failed")

        );
    }

    static Stream<Arguments> negativeCases() {
        return Stream.of(

                of("a = 'aaa; print(a);", 9,
                        "String type initialisation without closing quote test has not throw exception"),

                of("a = aaa'; print(a);", 4,
                        "String type initialisation without opening quote test has not throw exception"),

                of("a = 'aaa' + ; print(a);", 13,
                        "String concatenation without second operand test has not throw exception"),

                of("a = 'aaa' - 'bbb'; print(a);", 11,
                        "String concatenation with not supported operator '-' test has not throw exception"),

                of("a = 'aaa' / 'bbb'; print(a);", 11,
                        "String concatenation with not supported operator '/' test has not throw exception"),

                of("a = 'aaa' * 'bbb'; print(a);", 11,
                        "String concatenation with not supported operator '*' test has not throw exception"),

                of("a = ''; print(a);", 6,
                        "String type initialisation with null value test has not throw exception")

        );
    }
}
