package src.calculator.fsm.expression;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static src.calculator.fsm.expression.PrioritizedBinaryOperator.*;

public class BinaryOperatorFactory {

    private static final Logger logger = LoggerFactory.getLogger(BinaryOperatorFactory.class);

    private final Map<Character, PrioritizedBinaryOperator> binaryOperators = new HashMap<>();

    public BinaryOperatorFactory() {

        binaryOperators.put('+', new PrioritizedBinaryOperator(Priority.LOW, Double::sum));
        binaryOperators.put('-', new PrioritizedBinaryOperator(Priority.LOW, (left, right) -> left - right));
        binaryOperators.put('*', new PrioritizedBinaryOperator(Priority.MEDIUM, (left, right) -> left * right));
        binaryOperators.put('/', new PrioritizedBinaryOperator(Priority.MEDIUM, (left, right) -> left / right));
        binaryOperators.put('^', new PrioritizedBinaryOperator(Priority.HIGH, Math::pow));
    }

    public Optional<PrioritizedBinaryOperator> create(char operatorSymbol) {

        if (logger.isInfoEnabled()) {

            logger.info("Current binary operator -> {}", operatorSymbol);
        }

        return Optional.ofNullable(binaryOperators.get(operatorSymbol));
    }
}
