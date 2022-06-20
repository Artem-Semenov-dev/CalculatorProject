package com.teamdev.calculator.resolvers;

import com.teamdev.calculator.fsm.function.FunctionFactory;
import com.teamdev.calculator.fsm.function.FunctionMachine;
import com.teamdev.calculator.fsm.util.FunctionHolder;
import com.teamdev.calculator.math.MathElementResolver;
import com.teamdev.calculator.math.MathElementResolverFactory;
import com.teamdev.fsm.CharSequenceReader;
import com.teamdev.fsm.ResolvingException;

import java.util.Optional;

/**
 * {@code FunctionResolver} is an implementation of {@link MathElementResolver} that
 * resolve input chain for {@link FunctionMachine}.
 */

public class FunctionResolver implements MathElementResolver {

    private final MathElementResolverFactory elementResolverFactory;

    private final FunctionFactory functionFactory = new FunctionFactory();

    public FunctionResolver(MathElementResolverFactory factory) {

        this.elementResolverFactory = factory;
    }

    @Override
    public Optional<Double> resolve(CharSequenceReader inputChain) throws ResolvingException {

        FunctionHolder holder = new FunctionHolder();

        FunctionMachine functionMachine = FunctionMachine.create(elementResolverFactory);

        if (functionMachine.run(inputChain, holder)) {

            return Optional.ofNullable(functionFactory.create(holder.getFunctionName())
                    .evaluate(holder.getArguments()));
        }

        return Optional.empty();
    }

}
