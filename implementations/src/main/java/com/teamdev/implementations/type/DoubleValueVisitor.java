package com.teamdev.implementations.type;

public class DoubleValueVisitor implements ValueVisitor {

    private Double doubleValue;

    private boolean isDouble;

    private final boolean throwExceptionPermission;

    private DoubleValueVisitor(boolean throwExceptionPermission) {
        this.throwExceptionPermission = throwExceptionPermission;
    }

    @Override
    public void visit(DoubleValue value) {

        doubleValue = value.getValue();

        isDouble = true;
    }

    @Override
    public void visit(BooleanValue value) {

        if (throwExceptionPermission) {
            throw new IllegalArgumentException("Type mismatch: expected double but boolean provided");
        }

        isDouble = false;
    }

    @Override
    public void visit(DataStructureValue value) {

        if (throwExceptionPermission) {
            throw new IllegalArgumentException("Type mismatch: expected double but data structure provided");
        }

        isDouble = false;
    }

    private double getDoubleValue() {
        return doubleValue;
    }

    public static Double read(Value value) {
        DoubleValueVisitor doubleVisitor = new DoubleValueVisitor(true);

        value.accept(doubleVisitor);

        return doubleVisitor.getDoubleValue();
    }

    public static Boolean isDouble(Value value) {

        DoubleValueVisitor visitor = new DoubleValueVisitor(false);

        value.accept(visitor);

        return visitor.isDouble;

    }
}
