package com.teamdev.implementations.type;

public interface ValueVisitor {
    void visit(DoubleValue value);

    void visit(BooleanValue value);
}
