package com.teamdev.implementations.operators;

import com.teamdev.implementations.type.*;

import java.util.function.BiFunction;

public class StringBinaryOperator extends AbstractBinaryOperator{

    private final BiFunction<String, String, String> origin;

    public StringBinaryOperator(Priority priority, BiFunction<String, String, String> origin) {
        super(priority);
        this.origin = origin;
    }

    @Override
    public Value apply(Value left, Value right) {
        String leftOperand = StringValueVisitor.read(left);

        String rightOperand = StringValueVisitor.read(right);

        String result = origin.apply(leftOperand, rightOperand);

        return new StringValue(result);
    }
}
