package com.teamdev.implementations.type;

/**
 * {@code StringValueVisitor} is an implementation of {@link ValueVisitor} that
 * implement opportunity of read and use {@link StringValue}.
 */

public class StringValueVisitor implements ValueVisitor{

    private String stringValue;

    @Override
    public void visit(DoubleValue value) {
        stringValue = value.toString();
    }

    @Override
    public void visit(BooleanValue value) {
        stringValue = value.toString();
    }

    @Override
    public void visit(StringValue value) {
        stringValue = value.getStringValue();
    }

    private String getStringValue() {
        return stringValue;
    }

    public static String read(Value value){

        StringValueVisitor stringValueVisitor = new StringValueVisitor();

        value.accept(stringValueVisitor);

        return stringValueVisitor.getStringValue();
    }
}
