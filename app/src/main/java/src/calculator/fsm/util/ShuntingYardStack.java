package src.calculator.fsm.util;

import com.google.common.base.Preconditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.function.DoubleBinaryOperator;

/**
 * {@code ShuntingYardStack} is a data storing class for realization of concept of
 *  * <a href = "https://en.wikipedia.org/wiki/Shunting_yard_algorithm"> Shunting yard algorithm </a>
 */

public class ShuntingYardStack {

    private static final Logger logger = LoggerFactory.getLogger(ShuntingYardStack.class);

    private final Deque<Double> operandStack = new ArrayDeque<>();

    private final Deque<PrioritizedBinaryOperator> operatorStack = new ArrayDeque<>();

    public void pushOperand(double operand) {

        if (logger.isInfoEnabled()) {

            logger.info("Pushed operand -> {}", operand);
        }

        operandStack.push(operand);
    }

    public void pushOperator(PrioritizedBinaryOperator operator) {

        Preconditions.checkNotNull(operator);

        while (!operatorStack.isEmpty() && operatorStack.peek().compareTo(operator) >= 0) {

            actionTopOperator();
        }

        operatorStack.push(operator);
    }

    public double peekResult() {

        while (!operatorStack.isEmpty()) {

            actionTopOperator();
        }

        Preconditions.checkState(operandStack.size() == 1, "Operand stack has more than one result in the end of calculation");

        if (logger.isInfoEnabled()) {
            logger.info("Result -> {}", operandStack.peek());
            logger.info("------------------------------------------------------------------------------");
        }

        assert operandStack.peek() != null;
        return Preconditions.checkNotNull(operandStack.peek());
    }


    private void actionTopOperator() {
        Double rightOperand = operandStack.pop();

        Double leftOperand = operandStack.pop();

        DoubleBinaryOperator operator = operatorStack.pop();

        double result = operator.applyAsDouble(leftOperand, rightOperand);

        operandStack.push(result);
    }

    public double peekOperand() {
        assert operandStack.peek() != null;
        return Preconditions.checkNotNull(operandStack.peek());
    }

    public PrioritizedBinaryOperator peekOperator() {
        assert operatorStack.peek() != null;
        return Preconditions.checkNotNull(operatorStack.peek());
    }
}
