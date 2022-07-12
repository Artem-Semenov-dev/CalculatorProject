package com.teamdev.implementations.operators;

import com.teamdev.implementations.type.DoubleValue;
import com.teamdev.implementations.type.DoubleValueVisitor;
import com.teamdev.implementations.type.Value;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.UnaryOperator;

public class PrefixUnaryOperatorFactory implements UnaryOperatorFactory{

    private static final Logger logger = LoggerFactory.getLogger(PrefixUnaryOperatorFactory.class);

    private final Map<String, UnaryOperator<Value>> prefixUnaryOperators = new HashMap<>();

    public PrefixUnaryOperatorFactory() {

        prefixUnaryOperators.put("++", value -> {

            Double operand = DoubleValueVisitor.read(value);

            ++operand;

            return new DoubleValue(operand);
        });

        prefixUnaryOperators.put("--", value -> {

            Double operand = DoubleValueVisitor.read(value);

            --operand;

            return new DoubleValue(operand);
        });
    }

    @Override
    public Optional<UnaryOperator<Value>> create(String unaryOperator) {

        if (prefixUnaryOperators.containsKey(unaryOperator)){

            if (logger.isInfoEnabled()){

                logger.info("Current prefix unary operator -> {}", unaryOperator);
            }
        }

        return Optional.ofNullable(prefixUnaryOperators.get(unaryOperator));
    }
}
