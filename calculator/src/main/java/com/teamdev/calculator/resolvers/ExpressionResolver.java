package com.teamdev.calculator.resolvers;

import com.google.common.base.Preconditions;
import com.teamdev.calculator.fsm.expression.ExpressionMachine;
import com.teamdev.calculator.fsm.util.ShuntingYard;
import com.teamdev.calculator.math.MathElementResolver;
import com.teamdev.calculator.math.MathElementResolverFactory;
import com.teamdev.fsm.CharSequenceReader;
import com.teamdev.fsm.ResolvingException;

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
        ShuntingYard nestingShuntingYardStack = new ShuntingYard();

        ExpressionMachine expressionMachine = ExpressionMachine.create(factory);

        if (expressionMachine.run(inputChain, nestingShuntingYardStack)) {

            return Optional.of(nestingShuntingYardStack.peekResult());
        }

        return Optional.empty();
    }
}
