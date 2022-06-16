package com.teamdev.bazascript.calculator.resolvers;

import com.teamdev.bazascript.calculator.fsm.function.FunctionMachine;
import com.teamdev.bazascript.calculator.fsm.function.FunctionFactory;
import com.teamdev.bazascript.calculator.fsm.util.CharSequenceReader;
import com.teamdev.bazascript.calculator.fsm.util.FunctionHolder;
import com.teamdev.bazascript.calculator.fsm.util.ResolvingException;
import com.teamdev.bazascript.calculator.math.MathElementResolver;
import com.teamdev.bazascript.calculator.math.MathElementResolverFactory;

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
