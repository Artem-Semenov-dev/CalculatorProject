package com.teamdev.implementations.operators;

import com.teamdev.implementations.type.BooleanValue;
import com.teamdev.implementations.type.DoubleValue;
import com.teamdev.implementations.type.DoubleValueVisitor;
import com.teamdev.implementations.type.Value;

import java.util.function.BiFunction;

public class RelationalBinaryOperator extends AbstractBinaryOperator{

    private final BiFunction<Double, Double, Boolean> origin;

    public RelationalBinaryOperator(Priority priority, BiFunction<Double, Double, Boolean> origin) {
        super(priority);
        this.origin = origin;
    }

    @Override
    public Value apply(Value left, Value right) {
        double leftOperand = DoubleValueVisitor.read(left);

        double rightOperand = DoubleValueVisitor.read(right);

        Boolean result = origin.apply(leftOperand, rightOperand);

        return new BooleanValue(result);
    }
}
