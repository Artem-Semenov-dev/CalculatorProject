package src.calculator;

import com.google.common.base.Preconditions;
import src.calculator.fsm.DeadlockException;
import src.calculator.fsm.calculator.CalculatorMachine;
import src.calculator.fsm.expression.ShuntingYardStack;
import src.calculator.math.CharSequenceReader;
import src.calculator.math.MathElementResolverFactory;
import src.calculator.math.MathElementResolverFactoryImpl;
import src.calculator.math.ResolvingException;

import java.util.function.DoubleBinaryOperator;

public class Calculator {
    public CalculationResult calculate(MathematicalExpression expression) throws WrongExpressionException {
        Preconditions.checkNotNull(expression);

        MathElementResolverFactory factory = new MathElementResolverFactoryImpl();

        CalculatorMachine numberStateMachine = CalculatorMachine.create(factory);

        CharSequenceReader inputChain = new CharSequenceReader(expression.getExpression());
        ShuntingYardStack outputChain = new ShuntingYardStack();

        try {
            if (!numberStateMachine.run(inputChain, outputChain) || outputChain.peekResult() == infinity()) {

                raiseException(inputChain);
            }
        } catch (DeadlockException e) {
            raiseException(inputChain);
        } catch (ResolvingException e) {
            e.printStackTrace();
        }

        return new CalculationResult(outputChain.peekResult());
    }

    private static void raiseException(CharSequenceReader inputChain) throws WrongExpressionException {
        throw new WrongExpressionException("Wrong mathematical expression", inputChain.position());
    }

    private static double infinity() {
        DoubleBinaryOperator divisionByZero = (left, right) -> left / right;
        return divisionByZero.applyAsDouble(1, 0);
    }

}




























