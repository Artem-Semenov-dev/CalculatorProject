package com.teamdev.bazascript.interpreter.prefixoperator;

import com.google.common.base.Preconditions;
import com.teamdev.bazascript.interpreter.util.ExecutionException;
import com.teamdev.fsm.CharSequenceReader;
import com.teamdev.fsm.Transducer;
import com.teamdev.implementations.operators.PrefixUnaryOperatorFactory;
import com.teamdev.implementations.operators.UnaryOperatorFactory;
import com.teamdev.implementations.type.Value;

import java.util.Optional;
import java.util.function.UnaryOperator;

/**
 * {@code UnaryOperatorTransducer} is an implementation of {@link Transducer}
 * that used to produce and create unary operator to {@link UnaryPrefixOperatorContext}.
 */

class UnaryOperatorTransducer implements Transducer<UnaryPrefixOperatorContext, ExecutionException> {

    private final UnaryOperatorFactory factory = new PrefixUnaryOperatorFactory();

    @Override
    public boolean doTransition(CharSequenceReader inputChain, UnaryPrefixOperatorContext outputChain) {

        Preconditions.checkNotNull(inputChain, outputChain);

        if (!inputChain.canRead()) {
            return false;
        }

        Optional<UnaryOperator<Value>> unaryOperator = factory.create(inputChain.readOperator());

        if (unaryOperator.isPresent()) {

            outputChain.setUnaryOperator(unaryOperator.get());

            inputChain.incrementPosition();

            return true;
        }

        return false;
    }
}
