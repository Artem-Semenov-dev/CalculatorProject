package com.teamdev.calculator.resolvers;

import com.teamdev.calculator.fsm.calculator.DetachedShuntingYardTransducer;
import com.teamdev.calculator.fsm.function.FunctionFactory;
import com.teamdev.calculator.fsm.function.FunctionMachine;
import com.teamdev.calculator.fsm.util.FunctionHolder;
import com.teamdev.calculator.fsm.util.ShuntingYard;
import com.teamdev.calculator.math.MathElement;
import com.teamdev.calculator.math.MathElementResolver;
import com.teamdev.calculator.math.MathElementResolverFactory;
import com.teamdev.fsm.CharSequenceReader;
import com.teamdev.fsm.ResolvingException;

import java.util.Optional;
import java.util.function.BiConsumer;

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

        FunctionMachine<FunctionHolder> functionMachine = FunctionMachine.create(new DetachedShuntingYardTransducer<>(
                MathElement.EXPRESSION, FunctionHolder::setArgument, elementResolverFactory), FunctionHolder::setFunctionName);

        if (functionMachine.run(inputChain, holder)) {

            return Optional.ofNullable(functionFactory.create(holder.getFunctionName())
                    .evaluate(holder.getArguments()));
        }

        return Optional.empty();
    }

}
