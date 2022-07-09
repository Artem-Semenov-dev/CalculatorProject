package com.teamdev.implementations.operators;

import com.teamdev.implementations.type.Value;

import java.util.Optional;
import java.util.function.UnaryOperator;

public interface UnaryOperatorFactory {

    Optional<UnaryOperator<Value>> create(String unaryOperator);
}
