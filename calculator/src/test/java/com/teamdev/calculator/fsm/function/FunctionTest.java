package com.teamdev.calculator.fsm.function;

import com.teamdev.calculator.AbstractCalculatorTest;
import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

import static org.junit.jupiter.params.provider.Arguments.of;

class FunctionTest extends AbstractCalculatorTest {

    static Stream<Arguments> positiveCases(){
        return Stream.of(
                of("min(6,2,4)", 2.0, "Simple minimum function test has failed"),
                of("min(3*2,2,4)", 2.0, "Expression inside function test has failed"),
                of("min(min(6,9),max(1,2),4)", 2.0, "Nested functions test has failed"),
                of("max(1,2)", 2.0, "Simple maximum function test has failed"),
                of("avg(1,2)", 1.5, "Simple average function test has failed"),
                of("sqrt(4)", 2.0, "Simple sqrt function test has failed")
        );
    }

    static Stream<Arguments> negativeCases(){
        return Stream.of(
                of("min3,6)", 0, "Not opened bracket test has not throw exception"),
                of("min(3,6", 7, "Not closed bracket test has not throw exception")
        );
    }

}
