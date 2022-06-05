package src;

import org.junit.jupiter.api.Assertions;

public class PreparedTest {

    void positiveCase(String mathExpression, double resultExpected, String errorMessage) throws WrongExpressionException {
        CalculatorAPI calculator = new CalculatorAPI();
        CalculationResult result = calculator.calculate(new MathematicalExpression(mathExpression));

        Assertions.assertEquals(resultExpected, result.getResult(), errorMessage);
    }

    void negativeCase(String mathExpression, int expectedErrorPosition, String errorMessage){
        CalculatorAPI calculator = new CalculatorAPI();


        MathematicalExpression expression = new MathematicalExpression(mathExpression);

        WrongExpressionException exception =
                Assertions.assertThrows(WrongExpressionException.class, () -> calculator.calculate(expression));

        Assertions.assertEquals(expectedErrorPosition, exception.getErrorPosition(), errorMessage);
    }
}
