package com.teamdev.implementations.type;

import java.util.Collections;
import java.util.Map;

public class DataStructureValueVisitor implements ValueVisitor {

    private Map<String, Value> fields;

    private boolean isDataStructure;

    private final boolean throwExceptionPermission;

    private DataStructureValueVisitor(boolean throwExceptionPermission) {
        this.throwExceptionPermission = throwExceptionPermission;
    }

    @Override
    public void visit(DoubleValue value) {

        if (throwExceptionPermission) {
            throw new IllegalArgumentException("Type mismatch: expected data structure but double provided");
        }

        isDataStructure = false;
    }

    @Override
    public void visit(BooleanValue value) {

        if (throwExceptionPermission) {
            throw new IllegalArgumentException("Type mismatch: expected data structure but boolean provided");
        }

        isDataStructure = false;
    }

    @Override
    public void visit(DataStructureValue value) {

        fields = value.getFields();

        isDataStructure = true;
    }

    private Map<String, Value> getFields() {
        return Collections.unmodifiableMap(fields);
    }

    public static Map<String, Value> read(Value value){

        DataStructureValueVisitor visitor = new DataStructureValueVisitor(true);

        value.accept(visitor);

        return visitor.getFields();
    }

    public static Boolean isDataStructure(Value value){

        DataStructureValueVisitor visitor = new DataStructureValueVisitor(false);

        value.accept(visitor);

        return visitor.isDataStructure;
    }
}
