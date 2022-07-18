package com.teamdev.calculator.resolvers;

import com.teamdev.calculator.fsm.calculator.DetachedShuntingYardTransducer;
import com.teamdev.fsm.Transducer;
import com.teamdev.implementations.machines.function.FunctionFactory;
import com.teamdev.calculator.math.MathElement;
import com.teamdev.calculator.math.MathElementResolver;
import com.teamdev.calculator.math.MathElementResolverFactory;
import com.teamdev.fsm.CharSequenceReader;
import com.teamdev.calculator.ResolvingException;
import com.teamdev.implementations.datastructures.FunctionHolder;
import com.teamdev.implementations.machines.function.FunctionMachine;
import com.teamdev.implementations.type.Value;

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
    public Optional<Value> resolve(CharSequenceReader inputChain) throws ResolvingException {

        FunctionHolder holder = new FunctionHolder();

        var functionMachine = FunctionMachine.create(new DetachedShuntingYardTransducer<>(
                        MathElement.EXPRESSION, FunctionHolder::setArgument, elementResolverFactory),
                Transducer.checkAndPassChar('('),
                Transducer.checkAndPassChar(')'),
                FunctionHolder::setFunctionName,
                errorMessage -> {throw new ResolvingException(errorMessage);});

        if (functionMachine.run(inputChain, holder)) {

            return Optional.ofNullable(functionFactory.create(holder.getFunctionName())
                    .evaluate(holder.getArguments()));
        }

        return Optional.empty();
    }

}
