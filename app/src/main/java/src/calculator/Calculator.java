package src.calculator;

import com.google.common.base.Preconditions;
import src.calculator.fsm.DeadlockException;
import src.calculator.fsm.InputChain;
import src.calculator.fsm.Transducer;
import src.calculator.fsm.calculator.CalculatorMachine;
import src.calculator.fsm.expression.ShuntingYardStack;

import java.util.function.DoubleBinaryOperator;

public class Calculator {
    public CalculationResult calculate(MathematicalExpression expression) throws WrongExpressionException {
        Preconditions.checkNotNull(expression);

        Transducer<ShuntingYardStack> numberStateMachine = CalculatorMachine.create();

        InputChain inputChain = new InputChain(expression.getExpression());
        ShuntingYardStack outputChain = new ShuntingYardStack();

        try {
            if (!numberStateMachine.doTransition(inputChain, outputChain) || outputChain.peekResult() == infinity()) {

                raiseException(inputChain);
            }
        } catch (DeadlockException e) {
            raiseException(inputChain);
        }

        return new CalculationResult(outputChain.peekResult());
    }

    private static void raiseException(InputChain inputChain) throws WrongExpressionException {
        throw new WrongExpressionException("Wrong mathematical expression", inputChain.currentPosition());
    }

    private static double infinity() {
        DoubleBinaryOperator divisionByZero = (left, right) -> left / right;
        return divisionByZero.applyAsDouble(1, 0);
    }

}




























