package com.teamdev.implementations.type;

import java.util.Collections;
import java.util.Map;

public class DataStructureValue implements Value{

    private final Map<String, Value> fields;

    public DataStructureValue(Map<String, Value> fields) {
        this.fields = fields;
    }

    @Override
    public void accept(ValueVisitor visitor) {
        visitor.visit(this);
    }

    public Map<String, Value> getFields() {
        return Collections.unmodifiableMap(fields);
    }

    @Override
    public String toString() {
        return "DataStructureValue{" +
                "fields=" + fields +
                '}';
    }
}
