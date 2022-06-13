package src.calculator.fsm.number;

import org.junit.jupiter.params.provider.Arguments;
import src.calculator.AbstractCalculatorTest;

import java.util.stream.Stream;

import static org.junit.jupiter.params.provider.Arguments.of;

class NumberParcingTest extends AbstractCalculatorTest {

    static Stream<Arguments> positiveCases(){
        return Stream.of(
                of("4", 4.0, "Single digit test has failed."),
                of("-4", -4.0, "Negative single digit test has failed"),
                of("123", 123.0, "Several digits test has failed"),
                of("-123", -123.0, "Negative several digits test has failed"),
                of("123.78", 123.78, "Number with floating point test has failed"),
                of("-123.78", -123.78, "Negative number with floating point test has failed")
        );
    }

    static Stream<Arguments> negativeCases(){
        return Stream.of(
                of("--1", 1, "Extra negative sign has not failed the test"),
                of("-1.2.3", 4, "Second dot has not failed the test")
        );
    }
}
