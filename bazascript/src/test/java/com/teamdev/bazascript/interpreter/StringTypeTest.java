package com.teamdev.bazascript.interpreter;

import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

import static org.junit.jupiter.params.provider.Arguments.of;

class StringTypeTest extends AbstractTest{

    static Stream<Arguments> positiveCases() {
        return Stream.of(
                of("a = 'bytes'; b = 256 + ' ' + a; print(b);",
                        "[256.0 bytes]",
                        "String concatenation with number test has failed"),

                of("a = 'bytes'; b = 256 + 256 + a; print(b);",
                        "[256.0256.0bytes]",
                        "String concatenation with two number test has failed"),

                of("a = 'bytes'; b = a + ' ' + 256; print(b);",
                        "[bytes 256.0]",
                        "String concatenation with first string and second number test has failed"),

                of("a = 'Hello World!'; print(a);", "[Hello World!]",
                        "Simple string type test has failed"),

                of("firstName = 'John'; lastName = 'Doe'; fullName = firstName + ' ' + lastName; print(fullName);",
                        "[John Doe]",
                        "String concatenation test has failed"),

                of("a = 0; b = a < 2 ? 'One' : 'Two'; print(b);", "[One]",
                        "String type inside ternary operator test has failed")

        );
    }

    static Stream<Arguments> negativeCases() {
        return Stream.of(

                of("a = 'aaa; print(a);", 19,
                        "String type initialisation without closing quote test has not throw exception"),

                of("a = aaa'; print(a);", 7,
                        "String type initialisation without opening quote test has not throw exception"),

                of("a = 'aaa' + ; print(a);", 4,
                        "String concatenation without second operand test has not throw exception"),

                of("a = 'aaa' - 'bbb'; print(a);", 10,
                        "String concatenation with not supported operator '-' test has not throw exception"),

                of("a = 'aaa' / 'bbb'; print(a);", 10,
                        "String concatenation with not supported operator '/' test has not throw exception"),

                of("a = 'aaa' * 'bbb'; print(a);", 10,
                        "String concatenation with not supported operator '*' test has not throw exception"),

                of("a = ''; print(a);", 5,
                        "String type initialisation with null value test has not throw exception")

        );
    }
}
