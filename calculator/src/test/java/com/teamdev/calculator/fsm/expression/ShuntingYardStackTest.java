//package com.teamdev.calculator.fsm.expression;
//
//import com.teamdev.calculator.PreparedTest;
//import com.teamdev.calculator.WrongExpressionException;
//
//import com.teamdev.implementations.datastructures.ShuntingYard;
//import com.teamdev.implementations.operators.AbstractBinaryOperator;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.params.ParameterizedTest;
//import org.junit.jupiter.params.provider.CsvFileSource;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//
//public class ShuntingYardStackTest {
//
//    private final PreparedTest preparedTest = new PreparedTest();
//
//    private final ShuntingYard shuntingYardStack = new ShuntingYard();
//
//
//    @ParameterizedTest
//    @CsvFileSource(resources = "/CasesForShuntingYardTest")
//    void testPeekResult(double left, char symbol, double right, double expected, String errorMassage) throws WrongExpressionException {
//
//        preparedTest.ShuntingYardResultTest(left, symbol, right, expected, errorMassage);
//    }
//
//    @Test
//    void testPushOperand() {
//
//        shuntingYardStack.pushOperand(3);
//
//        assertEquals(3, shuntingYardStack.peekOperand());
//    }
//
//    @Test
//    void testPushOperator() {
//
//        AbstractBinaryOperator operator = new AbstractBinaryOperator(AbstractBinaryOperator.Priority.LOW, Double::sum);
//
//        shuntingYardStack.pushOperator(operator);
//
//        assertEquals(operator, shuntingYardStack.peekOperator());
//    }
//
//
//}
