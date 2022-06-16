package com.teamdev.bazascript.calculator;

import com.google.common.base.Preconditions;
import com.teamdev.bazascript.calculator.fsm.calculator.CalculatorMachine;
import com.teamdev.bazascript.calculator.fsm.util.CharSequenceReader;
import com.teamdev.bazascript.calculator.fsm.util.ResolvingException;
import com.teamdev.bazascript.calculator.fsm.util.ShuntingYardStack;
import com.teamdev.bazascript.calculator.math.MathElementResolverFactory;

import java.util.function.DoubleBinaryOperator;

/**
 * An API for resolving of math expressions. Math expression may contain:
 * - integer and float numbers;
 * - binary operators with priorities, e.g. +, -, *, /, ^ (power operator);
 * - brackets;
 * - functions, e.g. sum, min, max, log, log2, log10, sqrt, pi.
 * Raises a specific error if math expression is incorrect.
 *
 * <p>Typical usage scenario:
 * <p>
 * {@code
 * <p>
 * MathExpression expression = new MathExpression("2+2*3");
 * Calculator calculator = new Calculator();
 * Result result = calculator.calculate(expression);
 * <p>
 * }
 *
 *
 * <p> Implementation details: uses a set of finite state machines in order to
 * define grammar of math expression, parse elements of expression and calculate the result.
 */

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
        } catch (ResolvingException e) {
            raiseException(inputChain);
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




























