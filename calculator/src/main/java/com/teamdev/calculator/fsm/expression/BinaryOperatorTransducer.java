package com.teamdev.calculator.fsm.expression;

import com.google.common.base.Preconditions;
import com.teamdev.calculator.fsm.util.BinaryOperatorFactory;
import com.teamdev.calculator.fsm.util.PrioritizedBinaryOperator;
import com.teamdev.calculator.fsm.util.ShuntingYard;
import com.teamdev.fsm.CharSequenceReader;
import com.teamdev.fsm.Transducer;

import java.util.Optional;
import java.util.function.BiConsumer;

/**
 * {@code FunctionTransducer} is an implementation of {@link Transducer}
 * that produce an {@link PrioritizedBinaryOperator} to {@link ShuntingYard} output
 * for {@link ExpressionMachine}.
 */

class BinaryOperatorTransducer<O, E extends Exception> implements Transducer<O, E> {

    private final BinaryOperatorFactory factory = new BinaryOperatorFactory();

    private final BiConsumer<O, PrioritizedBinaryOperator> operatorConsumer;

    BinaryOperatorTransducer(BiConsumer<O, PrioritizedBinaryOperator> operatorConsumer) {

        this.operatorConsumer = Preconditions.checkNotNull(operatorConsumer);
    }

    @Override
    public boolean doTransition(CharSequenceReader inputChain, O outputChain) {

        Preconditions.checkNotNull(inputChain, outputChain);

        if (!inputChain.canRead()) {
            return false;
        }

        Optional<PrioritizedBinaryOperator> operator = factory.create(inputChain.read());

        if (operator.isPresent()) {

            operatorConsumer.accept(outputChain, operator.get());

            inputChain.incrementPosition();

            return true;
        }
        return false;
    }
}
