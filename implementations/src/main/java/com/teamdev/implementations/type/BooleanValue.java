package com.teamdev.implementations.type;

public class BooleanValue implements Value{

    private final boolean value;

    public BooleanValue(boolean value) {
        this.value = value;
    }

    @Override
    public void accept(ValueVisitor visitor) {
        visitor.visit(this);
    }

    boolean getBooleanValue() {
        return value;
    }

    @Override
    public String toString() {
        return Boolean.toString(value);
    }
}
