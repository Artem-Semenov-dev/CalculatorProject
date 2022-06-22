package com.teamdev.bazascript.interpreter;

import com.google.common.base.Preconditions;
import com.teamdev.calculator.fsm.util.PrioritizedBinaryOperator;
import com.teamdev.calculator.fsm.util.ShuntingYard;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Map;
import java.util.TreeMap;
import java.util.function.DoubleBinaryOperator;

public class ProgramMemory extends ShuntingYard {

    private final StringBuilder output = new StringBuilder();

    private static final Map<String, Double> variables = new TreeMap<>();

    private static final Logger logger = LoggerFactory.getLogger(ProgramMemory.class);

    private final Deque<Double> operandStack = new ArrayDeque<>();

    private final Deque<PrioritizedBinaryOperator> operatorStack = new ArrayDeque<>();

    public void setOutput(String output) {
        this.output.append(output);
    }

    StringBuilder getOutput() {
        return output;
    }

    @Override
    public void pushOperand(double operand) {

        if (logger.isInfoEnabled()) {

            logger.info("Pushed operand -> {}", operand);
        }

        operandStack.push(operand);
    }

    @Override
    public void pushOperator(PrioritizedBinaryOperator operator) {

        Preconditions.checkNotNull(operator);

        while (!operatorStack.isEmpty() && operatorStack.peek().compareTo(operator) >= 0) {

            actionTopOperator();
        }

        operatorStack.push(operator);
    }

    @Override
    public double peekResult() {

        while (!operatorStack.isEmpty()) {

            actionTopOperator();
        }

        Preconditions.checkState(operandStack.size() == 1, "Operand stack has more than one result in the end of calculation");

        if (logger.isInfoEnabled()) {
            logger.info("Peek operand -> {}", operandStack.peek());
//            logger.info("------------------------------------------------------------------------------");
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

    @Override
    public double peekOperand() {
        assert operandStack.peek() != null;
        return Preconditions.checkNotNull(operandStack.peek());
    }

    @Override
    public PrioritizedBinaryOperator peekOperator() {
        assert operatorStack.peek() != null;
        return Preconditions.checkNotNull(operatorStack.peek());
    }


    public void putVariable(String name, Double value){

        Preconditions.checkNotNull(name, value);

        variables.put(name, value);
    }

    public Double getVariable(String name){

        return variables.get(name);
    }

    public boolean hasVariable(String name){
        return variables.containsKey(name);
    }
}
