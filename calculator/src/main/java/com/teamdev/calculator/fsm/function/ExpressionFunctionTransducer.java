package com.teamdev.calculator.fsm.function;

import com.google.common.base.Preconditions;
import com.teamdev.calculator.fsm.util.FunctionHolder;
import com.teamdev.calculator.math.MathElementResolver;
import com.teamdev.fsm.CharSequenceReader;
import com.teamdev.fsm.ResolvingException;
import com.teamdev.fsm.Transducer;

import java.util.Optional;

/**
 * {@code ExpressionFunctionTransducer} is an implementation of {@link Transducer}
 * that produce an argument list for {@link FunctionHolder} output.
 */

class ExpressionFunctionTransducer implements Transducer<FunctionHolder> {

    private final MathElementResolver resolver;

    ExpressionFunctionTransducer(MathElementResolver resolver) {

        this.resolver = Preconditions.checkNotNull(resolver);
    }

    @Override
    public boolean doTransition(CharSequenceReader inputChain, FunctionHolder outputChain) throws ResolvingException {

        Optional<Double> resolve = resolver.resolve(inputChain);

        if (resolve.isPresent()){

            outputChain.setArgument(resolve.get());

            return true;
        }

        return false;
    }
}
