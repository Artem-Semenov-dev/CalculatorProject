package com.teamdev.calculator.fsm.function;

import com.google.common.base.Preconditions;
import com.teamdev.calculator.math.MathElementResolver;
import com.teamdev.fsm.CharSequenceReader;
import com.teamdev.calculator.ResolvingException;
import com.teamdev.fsm.Transducer;
import com.teamdev.implementations.datastructures.ShuntingYard;
import com.teamdev.implementations.type.Value;

import java.util.Optional;

/**
 * {@code FunctionTransducer} is an implementation of {@link Transducer}
 * that produce a number as a result of applying a function
 * to {@link ShuntingYard} output for {@link com.teamdev.implementations.machines.function.FunctionMachine}.
 */

public class FunctionTransducer implements Transducer<ShuntingYard, ResolvingException> {

    private final MathElementResolver resolver;

    public FunctionTransducer(MathElementResolver resolver) {
        this.resolver = Preconditions.checkNotNull(resolver);
    }

    @Override
    public boolean doTransition(CharSequenceReader inputChain, ShuntingYard outputChain) throws ResolvingException {

        Optional<Value> resolve = resolver.resolve(inputChain);
        if (resolve.isPresent()){
            outputChain.pushOperand(resolve.get());

            return true;
        }
        return false;
    }
}
