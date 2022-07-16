package com.teamdev.implementations.operators;

import java.util.Optional;
import java.util.Set;

public interface BinaryOperatorFactory {

    Optional<AbstractBinaryOperator> create(String operatorSign);

    Set<Character> getOperators();
}
