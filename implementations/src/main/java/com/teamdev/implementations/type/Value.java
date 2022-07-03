package com.teamdev.implementations.type;

public interface Value {

    void accept(ValueVisitor visitor);
}
