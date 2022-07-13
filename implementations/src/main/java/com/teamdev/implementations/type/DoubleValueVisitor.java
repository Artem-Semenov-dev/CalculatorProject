package com.teamdev.implementations.type;

import java.util.Optional;
import java.util.OptionalDouble;

public class DoubleValueVisitor implements ValueVisitor{
    private double doubleValue;

    @Override
    public void visit(DoubleValue value) {

        doubleValue = value.getValue();
    }

    @Override
    public void visit(BooleanValue value) {

        throw new IllegalArgumentException("Type mismatch: expected double but boolean provided");
    }

    public double getDoubleValue() {
        return doubleValue;
    }

    public static Double read(Value value){
        DoubleValueVisitor doubleVisitor = new DoubleValueVisitor();

        value.accept(doubleVisitor);

        return doubleVisitor.getDoubleValue();
    }
}
