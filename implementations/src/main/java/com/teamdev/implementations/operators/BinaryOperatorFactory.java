package com.teamdev.implementations.operators;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.BinaryOperator;

/**
 * {@code BinaryOperatorFactory} is a realization of factory pattern
 * that create an instance of {@link AbstractBinaryOperator} by symbol.
 */

public class BinaryOperatorFactory {

    private static final Logger logger = LoggerFactory.getLogger(BinaryOperatorFactory.class);

    private final Map<Character, AbstractBinaryOperator> binaryOperators = new HashMap<>();

    public BinaryOperatorFactory() {

        binaryOperators.put('+', new DoubleBinaryOperator(AbstractBinaryOperator.Priority.LOW, Double::sum));
        binaryOperators.put('-', new DoubleBinaryOperator(AbstractBinaryOperator.Priority.LOW, (left, right) -> left - right));
        binaryOperators.put('*', new DoubleBinaryOperator(AbstractBinaryOperator.Priority.MEDIUM, (left, right) -> left * right));
        binaryOperators.put('/', new DoubleBinaryOperator(AbstractBinaryOperator.Priority.MEDIUM, (left, right) -> left / right));
        binaryOperators.put('^', new DoubleBinaryOperator(AbstractBinaryOperator.Priority.HIGH, Math::pow));
    }

    public Optional<AbstractBinaryOperator> create(char operatorSymbol) {

        if (logger.isInfoEnabled()) {

            logger.info("Current binary operator -> {}", operatorSymbol);
        }

        return Optional.ofNullable(binaryOperators.get(operatorSymbol));
    }
}
