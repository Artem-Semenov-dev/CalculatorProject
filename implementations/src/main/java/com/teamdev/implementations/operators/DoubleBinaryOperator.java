package com.teamdev.implementations.operators;

import com.teamdev.implementations.type.*;

import java.util.Optional;
import java.util.function.BiFunction;

public class DoubleBinaryOperator extends AbstractBinaryOperator{

    private final BiFunction<Double, Double, Double> origin;

    public DoubleBinaryOperator(Priority priority, BiFunction<Double, Double, Double> origin) {
        super(priority);
        this.origin = origin;
    }

    @Override
    public Value apply(Value left, Value right) {

        double leftOperand = DoubleValueVisitor.read(left);

        double rightOperand = DoubleValueVisitor.read(right);

        Double result = origin.apply(leftOperand, rightOperand);

        return new DoubleValue(result);
    }
}
