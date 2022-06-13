package src.calculator.fsm.expression;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import src.calculator.PreparedTest;
import src.calculator.WrongExpressionException;
import src.calculator.fsm.util.PrioritizedBinaryOperator;
import src.calculator.fsm.util.ShuntingYardStack;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ShuntingYardStackTest {

    private final PreparedTest preparedTest = new PreparedTest();

    private final ShuntingYardStack shuntingYardStack = new ShuntingYardStack();


    @ParameterizedTest
    @CsvFileSource(resources = "/CasesForShuntingYardTest")
    void testPeekResult(double left, char symbol, double right, double expected, String errorMassage) throws WrongExpressionException {

        preparedTest.ShuntingYardResultTest(left, symbol, right, expected, errorMassage);
    }

    @Test
    void testPushOperand() {

        shuntingYardStack.pushOperand(3);

        assertEquals(3, shuntingYardStack.peekOperand());
    }

    @Test
    void testPushOperator() {

        PrioritizedBinaryOperator operator = new PrioritizedBinaryOperator(PrioritizedBinaryOperator.Priority.LOW, Double::sum);

        shuntingYardStack.pushOperator(operator);

        assertEquals(operator, shuntingYardStack.peekOperator());
    }


}
