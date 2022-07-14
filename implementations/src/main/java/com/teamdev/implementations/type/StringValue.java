package com.teamdev.implementations.type;

public class StringValue implements Value{

    private final String value;

    public StringValue(String value) {
        this.value = value;
    }

    @Override
    public void accept(ValueVisitor visitor) {

        visitor.visit(this);
    }

    public String getStringValue() {
        return value;
    }

}
