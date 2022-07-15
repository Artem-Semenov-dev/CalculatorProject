package com.teamdev.implementations.machines.expression;

import com.google.common.base.Preconditions;
import com.teamdev.fsm.CharSequenceReader;
import com.teamdev.fsm.Transducer;
import com.teamdev.implementations.operators.AbstractBinaryOperator;
import com.teamdev.implementations.operators.BinaryOperatorFactory;

import java.util.Optional;
import java.util.function.BiConsumer;

/**
 * {@code BinaryOperatorTransducer} is an implementation of {@link Transducer}
 * that produce an {@link AbstractBinaryOperator} to {@link com.teamdev.implementations.datastructures.ShuntingYard} output
 * for {@link ExpressionMachine}.
 */

public class BinaryOperatorTransducer<O, E extends Exception> implements Transducer<O, E> {

    private final BinaryOperatorFactory factory;

    private final BiConsumer<O, AbstractBinaryOperator> operatorConsumer;

    public BinaryOperatorTransducer(BinaryOperatorFactory factory, BiConsumer<O, AbstractBinaryOperator> operatorConsumer) {
        this.factory = factory;
        this.operatorConsumer = operatorConsumer;
    }

    @Override
    public boolean doTransition(CharSequenceReader inputChain, O outputChain) {

        Preconditions.checkNotNull(inputChain, outputChain);

        if (!inputChain.canRead()) {
            return false;
        }

        Optional<AbstractBinaryOperator> operator = factory.create(inputChain.readOperator());

        if (operator.isPresent()) {

            operatorConsumer.accept(outputChain, operator.get());

            inputChain.incrementPosition();

            return true;
        }
        if (inputChain.isOperator(inputChain.previous())) {
            inputChain.decrementPosition();
        }
        return false;
    }
}
