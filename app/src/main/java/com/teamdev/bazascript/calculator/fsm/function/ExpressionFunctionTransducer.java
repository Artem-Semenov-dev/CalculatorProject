package com.teamdev.bazascript.calculator.fsm.function;

import com.google.common.base.Preconditions;
import com.teamdev.bazascript.Transducer;
import com.teamdev.bazascript.calculator.fsm.util.CharSequenceReader;
import com.teamdev.bazascript.calculator.fsm.util.FunctionHolder;
import com.teamdev.bazascript.calculator.fsm.util.ResolvingException;
import com.teamdev.bazascript.calculator.math.MathElementResolver;

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
