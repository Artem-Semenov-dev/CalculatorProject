package com.teamdev.implementations.type;

public class DoubleValueVisitor implements ValueVisitor{
    private double doubleValue;

    @Override
    public void visit(DoubleValue value) {

        doubleValue = value.getValue();
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
