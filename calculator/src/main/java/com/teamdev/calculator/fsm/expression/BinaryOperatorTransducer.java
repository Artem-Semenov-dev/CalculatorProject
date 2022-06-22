package com.teamdev.calculator.fsm.expression;

import com.google.common.base.Preconditions;
import com.teamdev.calculator.fsm.util.BinaryOperatorFactory;
import com.teamdev.calculator.fsm.util.PrioritizedBinaryOperator;
import com.teamdev.calculator.fsm.util.ShuntingYard;
import com.teamdev.fsm.CharSequenceReader;
import com.teamdev.fsm.Transducer;

import java.util.Optional;

/**
 * {@code FunctionTransducer} is an implementation of {@link Transducer}
 * that produce an {@link PrioritizedBinaryOperator} to {@link ShuntingYard} output
 * for {@link ExpressionMachine}.
 */

class BinaryOperatorTransducer implements Transducer<ShuntingYard> {

    private final BinaryOperatorFactory factory = new BinaryOperatorFactory();

    @Override
    public boolean doTransition(CharSequenceReader inputChain, ShuntingYard outputChain) {

        Preconditions.checkNotNull(inputChain, outputChain);

        if (!inputChain.canRead()) {
            return false;
        }

        Optional<PrioritizedBinaryOperator> operator = factory.create(inputChain.read());

        if (operator.isPresent()) {
            outputChain.pushOperator(operator.get());

            inputChain.incrementPosition();

            return true;
        }
        return false;
    }
}
