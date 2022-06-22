package com.teamdev.calculator;

import com.teamdev.calculator.fsm.util.BinaryOperatorFactory;
import com.teamdev.calculator.fsm.util.PrioritizedBinaryOperator;
import com.teamdev.calculator.fsm.util.ShuntingYard;
import org.junit.jupiter.api.Assertions;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PreparedTest {

    public void positiveCase(String mathExpression, double resultExpected, String errorMessage) throws WrongExpressionException {
        Calculator calculator = new Calculator();
        CalculationResult result = calculator.calculate(new MathematicalExpression(mathExpression));

        assertEquals(resultExpected, result.getResult(), errorMessage);
    }

    public void negativeCase(String mathExpression, int expectedErrorPosition, String errorMessage) {
        Calculator calculator = new Calculator();


        MathematicalExpression expression = new MathematicalExpression(mathExpression);

        WrongExpressionException exception =
                Assertions.assertThrows(WrongExpressionException.class, () -> calculator.calculate(expression));

        assertEquals(expectedErrorPosition, exception.getErrorPosition(), errorMessage);
    }

    public void ShuntingYardResultTest(double left, char symbol, double right, double expected, String errorMassage) {

        ShuntingYard shuntingYardStack = new ShuntingYard();

        Optional<PrioritizedBinaryOperator> operator;

        final BinaryOperatorFactory factory = new BinaryOperatorFactory();

        shuntingYardStack.pushOperand(left);

        operator = factory.create(symbol);

        operator.ifPresent(shuntingYardStack::pushOperator);

        shuntingYardStack.pushOperand(right);

        double result = shuntingYardStack.peekResult();

        assertEquals(expected, result, errorMassage);
    }
}
