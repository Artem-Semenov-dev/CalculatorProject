package src;

import org.junit.jupiter.api.Assertions;
import src.fsm.expression.BinaryOperatorFactory;
import src.fsm.expression.PrioritizedBinaryOperator;
import src.fsm.expression.ShuntingYardStack;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PreparedTest {

    public void positiveCase(String mathExpression, double resultExpected, String errorMessage) throws WrongExpressionException {
        CalculatorAPI calculator = new CalculatorAPI();
        CalculationResult result = calculator.calculate(new MathematicalExpression(mathExpression));

        assertEquals(resultExpected, result.getResult(), errorMessage);
    }

    public void negativeCase(String mathExpression, int expectedErrorPosition, String errorMessage){
        CalculatorAPI calculator = new CalculatorAPI();


        MathematicalExpression expression = new MathematicalExpression(mathExpression);

        WrongExpressionException exception =
                Assertions.assertThrows(WrongExpressionException.class, () -> calculator.calculate(expression));

        assertEquals(expectedErrorPosition, exception.getErrorPosition(), errorMessage);
    }

    public void ShuntingYardResultTest(double left, char symbol, double right, double expected, String errorMassage ){

        ShuntingYardStack shuntingYardStack = new ShuntingYardStack();

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
