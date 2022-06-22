package com.teamdev.bazascript.interpreter.resolvers;

import com.teamdev.bazascript.interpreter.ProgramMemory;
import com.teamdev.bazascript.interpreter.procedure.ProcedureFactory;
import com.teamdev.calculator.fsm.function.FunctionFactory;
import com.teamdev.calculator.fsm.function.FunctionMachine;
import com.teamdev.calculator.fsm.util.FunctionHolder;
import com.teamdev.calculator.math.MathElementResolver;
import com.teamdev.calculator.math.MathElementResolverFactory;
import com.teamdev.fsm.CharSequenceReader;
import com.teamdev.fsm.ResolvingException;

import java.util.Optional;

public class FunctionResolver implements MathElementResolver {

    private final MathElementResolverFactory elementResolverFactory;

    private final FunctionFactory procedureFactory = new FunctionFactory();

    public FunctionResolver(MathElementResolverFactory factory) {

        this.elementResolverFactory = factory;
    }

    @Override
    public Optional<Double> resolve(CharSequenceReader inputChain) throws ResolvingException {

        FunctionHolder holder = new FunctionHolder();

        FunctionMachine functionMachine = FunctionMachine.create(elementResolverFactory);

        if (functionMachine.run(inputChain, holder)) {

            ProgramMemory output = new ProgramMemory();

            return Optional.ofNullable(procedureFactory.create(holder.getFunctionName())
                    .evaluate(holder.getArguments()));
        }

        return Optional.empty();
    }
}
