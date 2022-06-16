package com.teamdev.bazascript.calculator;

import com.teamdev.bazascript.calculator.CalculationResult;
import com.teamdev.bazascript.calculator.Calculator;
import com.teamdev.bazascript.calculator.MathematicalExpression;
import com.teamdev.bazascript.calculator.WrongExpressionException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

public abstract class AbstractCalculatorTest {

    private final Calculator calculator = new Calculator();

    @ParameterizedTest
    @MethodSource("positiveCases")
    void positiveCase(String mathExpression, double resultExpected, String errorMessage) throws WrongExpressionException {

        CalculationResult result = calculator.calculate(new MathematicalExpression(mathExpression));

        assertEquals(resultExpected, result.getResult(), errorMessage);
    }

    @ParameterizedTest
    @MethodSource("negativeCases")
    void negativeCase(String mathExpression, int expectedErrorPosition, String errorMessage) {

        MathematicalExpression expression = new MathematicalExpression(mathExpression);

        WrongExpressionException exception =
                Assertions.assertThrows(WrongExpressionException.class, () -> calculator.calculate(expression));

        assertEquals(expectedErrorPosition, exception.getErrorPosition(), errorMessage);
    }
}
