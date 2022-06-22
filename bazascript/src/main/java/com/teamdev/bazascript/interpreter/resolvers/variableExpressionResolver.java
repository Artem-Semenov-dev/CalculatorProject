package com.teamdev.bazascript.interpreter.resolvers;

import com.google.common.base.Preconditions;
import com.teamdev.bazascript.interpreter.ProgramMemory;
import com.teamdev.calculator.fsm.expression.ExpressionMachine;
import com.teamdev.calculator.math.MathElementResolver;
import com.teamdev.calculator.math.MathElementResolverFactory;
import com.teamdev.fsm.CharSequenceReader;
import com.teamdev.fsm.ResolvingException;

import java.util.Optional;

public class variableExpressionResolver implements MathElementResolver {

    private final MathElementResolverFactory factory;

    public variableExpressionResolver(MathElementResolverFactory factory) {
        this.factory = Preconditions.checkNotNull(factory);
    }

    @Override
    public Optional<Double> resolve(CharSequenceReader inputChain) throws ResolvingException {
        ProgramMemory nestingShuntingYardStack = new ProgramMemory();

        ExpressionMachine expressionMachine = ExpressionMachine.create(factory);

        if (expressionMachine.run(inputChain, nestingShuntingYardStack)) {

            return Optional.of(nestingShuntingYardStack.peekResult());
        }

        return Optional.empty();
    }
}
