package com.teamdev.calculator.fsm.calculator;

import com.google.common.base.Preconditions;
import com.teamdev.calculator.fsm.util.ShuntingYard;
import com.teamdev.calculator.math.MathElement;
import com.teamdev.calculator.math.MathElementResolver;
import com.teamdev.calculator.math.MathElementResolverFactory;
import com.teamdev.fsm.CharSequenceReader;
import com.teamdev.fsm.ResolvingException;
import com.teamdev.fsm.Transducer;

import java.util.Optional;
import java.util.function.BiConsumer;

/**
 * {@code DetachedShuntingYardTransducer} is a universal implementation of {@link Transducer}
 * that can be used for state machines which work based on new instances of {@link ShuntingYard}.
 * <p>
 * New instance of {@link ShuntingYard} is required for all operations with brackets.
 * </p>
 */

public class DetachedShuntingYardTransducer<O> implements Transducer<O> {

    private final MathElementResolverFactory factory;

    private final MathElement resolverType;

    private final BiConsumer<O, Double> resultConsumer;

    public DetachedShuntingYardTransducer(MathElement resolver, BiConsumer<O, Double> resultConsumer,
                                          MathElementResolverFactory factory) {
        this.resolverType =  Preconditions.checkNotNull(resolver);
        this.resultConsumer = Preconditions.checkNotNull(resultConsumer);
        this.factory = factory;
    }

    @Override
    public boolean doTransition(CharSequenceReader inputChain, O outputChain) throws ResolvingException {

        MathElementResolver resolver = factory.create(resolverType);

        Optional<Double> resolveResult = resolver.resolve(inputChain);

        resolveResult.ifPresent((Double value) -> resultConsumer.accept(outputChain, value));

        return resolveResult.isPresent();
    }
}
