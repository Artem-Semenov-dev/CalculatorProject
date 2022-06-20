package com.teamdev.calculator;

import com.google.common.base.Preconditions;
import com.teamdev.calculator.fsm.brackets.BracketsMachine;
import com.teamdev.calculator.fsm.operand.OperandMachine;
import com.teamdev.calculator.math.MathElement;
import com.teamdev.calculator.math.MathElementResolver;
import com.teamdev.calculator.math.MathElementResolverFactory;
import com.teamdev.calculator.resolvers.DetachedShuntingYardResolver;
import com.teamdev.calculator.resolvers.ExpressionResolver;
import com.teamdev.calculator.resolvers.FunctionResolver;
import com.teamdev.calculator.resolvers.NumberResolver;

import java.util.EnumMap;
import java.util.Map;

import static com.teamdev.calculator.math.MathElement.*;

public class MathElementResolverFactoryImpl implements MathElementResolverFactory {

    private final Map<MathElement, MathElementResolverCreator> resolvers = new EnumMap<>(MathElement.class);

    MathElementResolverFactoryImpl() {

        resolvers.put(NUMBER, NumberResolver::new);

        resolvers.put(EXPRESSION, () -> new ExpressionResolver(this));

//        resolvers.put(EXPRESSION, () -> new DetachedShuntingYardResolvers<>(ExpressionMachine.create(this)));

        resolvers.put(OPERAND, () -> new DetachedShuntingYardResolver<>(OperandMachine.create(this)));

        resolvers.put(BRACKETS, () -> new DetachedShuntingYardResolver<>(BracketsMachine.create(this)));

        resolvers.put(FUNCTION, () -> new FunctionResolver(this));
    }


    @Override
    public MathElementResolver create(MathElement mathElement) {
        Preconditions.checkState(resolvers.containsKey(Preconditions.checkNotNull(mathElement)));

        MathElementResolverCreator resolverCreator = resolvers.get(mathElement);

        return resolverCreator.create();
    }

    @FunctionalInterface
    interface MathElementResolverCreator{

        MathElementResolver create();
    }
}
