package com.teamdev.bazascript.interpreter.datastructure;

import com.teamdev.bazascript.interpreter.AbstractTest;
import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

import static org.junit.jupiter.params.provider.Arguments.of;

public class DataStructureTest extends AbstractTest {

    static Stream<Arguments> positiveCases() {
        return Stream.of(
                of("Point {x, y}; a = Point{1, 2}; print(a.x, a.y);", "[1.0, 2.0]",
                        "Data structure defining and initialisation with variable of type Point test has failed."),

                of("Point {x, y}; a = Point{1, 2}; b = Point{3, 4}; print(a.x, a.y); print(b.x, b.y);", "[1.0, 2.0][3.0, 4.0]",
                        "Data structure defining and initialisation with two variables of type Point test has failed."),

                of("Point {x, y}; a = Point{1, 2}; b = Point{3, 4}; c = a.x; r = b.y; print(c, r)", "[1.0, 4.0]",
                        "Initialisation variables with data structure fields test has failed."),

                of("Point {x, y}; a = Point{1, 2}; b = a.x < 2 ? 3 : 8; print(b)", "[8.0]",
                        "ternary operator with data structure field test has failed.")

        );

    }

    static Stream<Arguments> negativeCases() {
        return Stream.of(

                of("Point {x, y}; a = Point{1, 2}; print(a.t);", " [1.0, 2.0]", 18,
                        "Access to not defined field in data structure test has not throw exception"),

                of("Point x, y}; a = Point{1, 2}; print(a.x, a.y);", " [1.0, 2.0]", 18,
                        "Data structure defining without '{' test has not throw exception"),

                of("Point {x, y; a = Point{1, 2}; print(a.x, a.y);", " [1.0, 2.0]", 18,
                        "Data structure defining without '}' test has not throw exception"),

                of("Point {x, y}; a = Po{1, 2}; print(a.x, a.y);", " [1.0, 2.0]", 18,
                        "Access to a not defined data structure test has not throw exception")

        );
    }
}
