package com.teamdev.bazascript.calculator.resolvers;

import com.google.common.base.Preconditions;
import com.teamdev.bazascript.calculator.fsm.expression.ExpressionMachine;
import com.teamdev.bazascript.calculator.fsm.util.CharSequenceReader;
import com.teamdev.bazascript.calculator.fsm.util.ResolvingException;
import com.teamdev.bazascript.calculator.fsm.util.ShuntingYardStack;
import com.teamdev.bazascript.calculator.math.MathElementResolver;
import com.teamdev.bazascript.calculator.math.MathElementResolverFactory;

import java.util.Optional;

/**
 * {@code ExpressionResolver} is an implementation of {@link MathElementResolver} that
 * resolve input chain for {@link ExpressionMachine}.
 */

public class ExpressionResolver implements MathElementResolver {

    private final MathElementResolverFactory factory;

    public ExpressionResolver(MathElementResolverFactory factory) {
        this.factory = Preconditions.checkNotNull(factory);
    }

    @Override
    public Optional<Double> resolve(CharSequenceReader inputChain) throws ResolvingException {
        ShuntingYardStack nestingShuntingYardStack = new ShuntingYardStack();

        ExpressionMachine expressionMachine = ExpressionMachine.create(factory);

        if (expressionMachine.run(inputChain, nestingShuntingYardStack)) {

            return Optional.of(nestingShuntingYardStack.peekResult());
        }

        return Optional.empty();
    }
}
