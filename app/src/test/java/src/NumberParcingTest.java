package src;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NumberParcingTest {

    CalculatorAPI calculator = new CalculatorAPI();

//    private static final Logger logger = LoggerFactory.getLogger(NumberParcingTest.class);

    @ParameterizedTest
    @CsvSource(value = {
            "4, 4.0, Single digit test has failed",
            "-4, -4.0, Negative single digit test has failed",
            "123, 123.0, Several digits test has failed",
            "-123, -123.0, Negative several digits test has failed",
            "123.78, 123.78, Number with floating point test has failed",
            "-123.78, -123.78, Negative number with floating point test has failed",

    })
    void testPositiveCase(String mathExpression, double resultExpected, String errorMessage) throws WrongExpressionException {

        CalculationResult result = calculator.calculate(new MathematicalExpression(mathExpression));

        Assertions.assertEquals(resultExpected, result.getResult(), errorMessage);
    }

    @ParameterizedTest
    @CsvSource(value = {
            "--1, 1, Extra negative sign has not failed the test",
            "-1.2.3, 4, Second dot has not failed the test"
    })
    void testNegativeCase(String mathExpression, int expectedErrorPosition, String errorMessage) throws WrongExpressionException {

        MathematicalExpression expression = new MathematicalExpression(mathExpression);

        WrongExpressionException exception =
                Assertions.assertThrows(WrongExpressionException.class, () -> calculator.calculate(expression));

        Assertions.assertEquals(expectedErrorPosition, exception.getErrorPosition(), errorMessage);
    }


}
