package com.teamdev.implementations.datastructures;

import com.google.common.base.Preconditions;
import com.teamdev.implementations.operators.AbstractBinaryOperator;
import com.teamdev.implementations.type.Value;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * {@code ShuntingYardStack} is a data storing class for realization of concept of
 *  * <a href = "https://en.wikipedia.org/wiki/Shunting_yard_algorithm"> Shunting yard algorithm </a>
 */

public class ShuntingYard {

    private static final Logger logger = LoggerFactory.getLogger(ShuntingYard.class);

    private final Deque<Value> operandStack = new ArrayDeque<>();

    private final Deque<AbstractBinaryOperator> operatorStack = new ArrayDeque<>();

    public void pushOperand(Value operand) {

        if (logger.isInfoEnabled()) {

            logger.info("Pushed operand -> {}", operand);
        }

        operandStack.push(operand);
    }

    public void pushOperator(AbstractBinaryOperator operator) {

        Preconditions.checkNotNull(operator);

        while (!operatorStack.isEmpty() && operatorStack.peek().compareTo(operator) >= 0) {

            actionTopOperator();
        }

        operatorStack.push(operator);
    }

    public Value popResult() {

        while (!operatorStack.isEmpty()) {

            actionTopOperator();
        }

        return operandStack.pop();
    }


    private void actionTopOperator() {
        Value rightOperand = operandStack.pop();

        Value leftOperand = operandStack.pop();

        AbstractBinaryOperator operator = operatorStack.pop();

        Value result = operator.apply(leftOperand, rightOperand);

        operandStack.push(result);
    }

}
