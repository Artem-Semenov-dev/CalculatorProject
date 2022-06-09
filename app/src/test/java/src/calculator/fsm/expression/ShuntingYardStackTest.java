package src.calculator.fsm.expression;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import src.calculator.PreparedTest;
import src.calculator.WrongExpressionException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ShuntingYardStackTest {

    PreparedTest preparedTest = new PreparedTest();

    ShuntingYardStack shuntingYardStack = new ShuntingYardStack();


    @ParameterizedTest
    @CsvFileSource(resources = "/CasesForShuntingYardTest")
    void testPeekResult(double left, char symbol, double right, double expected, String errorMassage) throws WrongExpressionException {

        preparedTest.ShuntingYardResultTest(left, symbol, right, expected, errorMassage);
    }

    @Test
    public void testPushOperand() {

        shuntingYardStack.pushOperand(3);

        assertEquals(3, shuntingYardStack.peekOperand());
    }

    @Test
    public void testPushOperator() {

        PrioritizedBinaryOperator operator = new PrioritizedBinaryOperator(PrioritizedBinaryOperator.Priority.LOW, Double::sum);

        shuntingYardStack.pushOperator(operator);

        assertEquals(operator, shuntingYardStack.peekOperator());
    }


}
