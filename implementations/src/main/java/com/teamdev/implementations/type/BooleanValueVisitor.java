package com.teamdev.implementations.type;

public class BooleanValueVisitor implements ValueVisitor{

    private boolean booleanValue;

    private boolean isBoolean;

    private final boolean throwExceptionPermission;

    private BooleanValueVisitor(boolean throwExceptionPermission) {
        this.throwExceptionPermission = throwExceptionPermission;
    }

    @Override
    public void visit(DoubleValue value) {
        if (throwExceptionPermission) {
            throw new IllegalArgumentException("Type mismatch: expected boolean but double provided");
        }

        isBoolean = false;
    }

    @Override
    public void visit(BooleanValue value) {

        booleanValue = value.getBooleanValue();

        isBoolean = true;
    }

    private boolean getBooleanValue(){
        return booleanValue;
    }

    public static Boolean read(Value value){
        BooleanValueVisitor booleanValueVisitor = new BooleanValueVisitor(true);

        value.accept(booleanValueVisitor);

        return booleanValueVisitor.getBooleanValue();
    }

    public static Boolean isBoolean(Value value) {

        BooleanValueVisitor visitor = new BooleanValueVisitor(false);

        value.accept(visitor);

        return visitor.isBoolean;

    }
}
