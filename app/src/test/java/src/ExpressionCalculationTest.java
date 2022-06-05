package src;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class ExpressionCalculationTest {
    CalculatorAPI calculator = new CalculatorAPI();

    @ParameterizedTest
    @CsvSource(value = {
            "1+2, 3.0, Simple sum action test has failed",
            "2*4, 8.0, Simple multiplication action test has failed",
            "1+2*3, 7.0, Several digits test has failed",
            "3*4+6*7+9^2, 135.0, Negative several digits test has failed",
            "2 * 4, 8.0, Space between operands test has failed",
    })
    void testPositiveCase(String mathExpression, double resultExpected, String errorMessage) throws WrongExpressionException {

        CalculationResult result = calculator.calculate(new MathematicalExpression(mathExpression));

        Assertions.assertEquals(resultExpected, result.getResult(), errorMessage);
    }

    @ParameterizedTest
    @CsvSource(value = {
            "1/0, 3, Division by zero test has failed",
            "1**2, 2, Double minus action test has failed",
    })
    void testNegativeCase(String mathExpression, int expectedErrorPosition, String errorMessage) {

        MathematicalExpression expression = new MathematicalExpression(mathExpression);

        WrongExpressionException exception =
                Assertions.assertThrows(WrongExpressionException.class, () -> calculator.calculate(expression));

        Assertions.assertEquals(expectedErrorPosition, exception.getErrorPosition(), errorMessage);
    }
}
