package com.teamdev.calculator.fsm.calculator;

import com.google.common.base.Preconditions;
import com.teamdev.calculator.fsm.util.ShuntingYardStack;
import com.teamdev.calculator.math.MathElementResolver;
import com.teamdev.fsm.CharSequenceReader;
import com.teamdev.fsm.ResolvingException;
import com.teamdev.fsm.Transducer;

import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

/**
 * {@code DetachedShuntingYardTransducer} is a universal implementation of {@link Transducer}
 * that can be used for state machines which work based on new instances of {@link ShuntingYardStack}.
 * <p>
 * New instance of {@link ShuntingYardStack} is required for all operations with brackets.
 * </p>
 */

public class DetachedShuntingYardTransducer<O> implements Transducer<O> {

    private final MathElementResolver resolver;

    private final BiConsumer<O,Double> resultConsumer;

    public DetachedShuntingYardTransducer(MathElementResolver resolver, BiConsumer<O, Double> resultConsumer) {
        this.resolver = Preconditions.checkNotNull(resolver);
        this.resultConsumer = Preconditions.checkNotNull(resultConsumer);
    }

    @Override
    public boolean doTransition(CharSequenceReader inputChain, O outputChain) throws ResolvingException {
        Optional<Double> resolve = resolver.resolve(inputChain);

        resolve.ifPresent(value -> resultConsumer.accept(outputChain, value));

        return resolve.isPresent();
    }
}
