package com.teamdev.bazascript.calculator.fsm.expression;

import com.google.common.base.Preconditions;
import com.teamdev.bazascript.calculator.fsm.util.BinaryOperatorFactory;
import com.teamdev.bazascript.calculator.fsm.util.PrioritizedBinaryOperator;
import com.teamdev.bazascript.calculator.fsm.util.ShuntingYardStack;
import com.teamdev.bazascript.Transducer;
import com.teamdev.bazascript.calculator.fsm.util.CharSequenceReader;

import java.util.Optional;

/**
 * {@code FunctionTransducer} is an implementation of {@link Transducer}
 * that produce an {@link PrioritizedBinaryOperator} to {@link ShuntingYardStack} output
 * for {@link ExpressionMachine}.
 */

class BinaryOperatorTransducer implements Transducer<ShuntingYardStack> {

    private final BinaryOperatorFactory factory = new BinaryOperatorFactory();

    @Override
    public boolean doTransition(CharSequenceReader inputChain, ShuntingYardStack outputChain) {

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
