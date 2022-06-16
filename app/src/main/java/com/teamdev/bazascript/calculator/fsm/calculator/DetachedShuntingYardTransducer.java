package com.teamdev.bazascript.calculator.fsm.calculator;

import com.google.common.base.Preconditions;
import com.teamdev.bazascript.Transducer;
import com.teamdev.bazascript.calculator.fsm.util.CharSequenceReader;
import com.teamdev.bazascript.calculator.fsm.util.ResolvingException;
import com.teamdev.bazascript.calculator.fsm.util.ShuntingYardStack;
import com.teamdev.bazascript.calculator.math.MathElementResolver;

import java.util.Optional;

/**
 * {@code DetachedShuntingYardTransducer} is a universal implementation of {@link Transducer}
 * that can be used for state machines which work based on new instances of {@link ShuntingYardStack}.
 * <p>
 * New instance of {@link ShuntingYardStack} is required for all operations with brackets.
 * </p>
 */

public class DetachedShuntingYardTransducer implements Transducer<ShuntingYardStack> {

    private final MathElementResolver resolver;

    public DetachedShuntingYardTransducer(MathElementResolver resolver) {
        this.resolver = Preconditions.checkNotNull(resolver);
    }

    @Override
    public boolean doTransition(CharSequenceReader inputChain, ShuntingYardStack outputChain) throws ResolvingException {
        Optional<Double> resolve = resolver.resolve(inputChain);
        if (resolve.isPresent()){
            outputChain.pushOperand(resolve.get());

            return true;
        }
        return false;
    }
}
