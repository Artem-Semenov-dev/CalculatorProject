package src;

import com.google.common.base.Preconditions;
import src.fsm.DeadlockException;
import src.fsm.InputChain;
import src.fsm.Transducer;
import src.fsm.calculator.CalculatorMachine;
import src.fsm.expression.ShuntingYardStack;

import java.util.function.DoubleBinaryOperator;


public class CalculatorAPI {
    public CalculationResult calculate(MathematicalExpression expression) throws WrongExpressionException {
        Preconditions.checkNotNull(expression);

        Transducer<ShuntingYardStack> numberStateMachine = CalculatorMachine.create();

        InputChain inputChain = new InputChain(expression.getExpression());
        ShuntingYardStack outputChain = new ShuntingYardStack();

        try {
            if (!numberStateMachine.doTransition(inputChain, outputChain) ) {

                raiseException(inputChain);
            }
        } catch (DeadlockException e) {
            raiseException(inputChain);
        }

        return new CalculationResult(outputChain.popResult());
    }

    private void raiseException(InputChain inputChain) throws WrongExpressionException {
        throw new WrongExpressionException("Wrong mathematical expression", inputChain.currentPosition());
    }

    private double Infinity(){
        DoubleBinaryOperator divisionByZero = (left, right) -> left/right;
        return divisionByZero.applyAsDouble(1, 0);
    }

}




























