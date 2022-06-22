package com.teamdev.bazascript.interpreter.resolvers;

import com.teamdev.calculator.fsm.function.FunctionFactory;
import com.teamdev.calculator.math.MathElementResolver;
import com.teamdev.calculator.math.MathElementResolverFactory;
import com.teamdev.fsm.CharSequenceReader;
import com.teamdev.fsm.ResolvingException;

import java.util.Optional;

public class ProcedureResolver implements MathElementResolver {

    private final MathElementResolverFactory elementResolverFactory;

    private final FunctionFactory functionFactory = new FunctionFactory();

    public ProcedureResolver(MathElementResolverFactory elementResolverFactory) {
        this.elementResolverFactory = elementResolverFactory;
    }

    @Override
    public Optional<Double> resolve(CharSequenceReader inputChain) throws ResolvingException {
        return Optional.empty();
    }
}
