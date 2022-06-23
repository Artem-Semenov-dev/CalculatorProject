package com.teamdev.bazascript.interpreter.util;

import com.google.common.base.Preconditions;
import com.teamdev.bazascript.interpreter.operand.OperandMachine;
import com.teamdev.bazascript.interpreter.resolvers.*;
import com.teamdev.calculator.MathElementResolverCreator;
import com.teamdev.calculator.math.MathElement;
import com.teamdev.calculator.math.MathElementResolver;
import com.teamdev.calculator.math.MathElementResolverFactory;
import com.teamdev.calculator.resolvers.NumberResolver;

import java.util.EnumMap;
import java.util.Map;

import static com.teamdev.calculator.math.MathElement.*;

public class CalculatorElementResolverFactory implements MathElementResolverFactory {

    private final Map<MathElement, MathElementResolverCreator> resolvers = new EnumMap<>(MathElement.class);

    public CalculatorElementResolverFactory() {
        resolvers.put(NUMBER, NumberResolver::new);

        resolvers.put(EXPRESSION, () -> new VariableExpressionResolver(this));

        resolvers.put(OPERAND, () -> new DetachedMemoryResolver<>(OperandMachine.create(this)));

        resolvers.put(BRACKETS, () -> new BracketsResolver(this));

        resolvers.put(FUNCTION, () -> new FunctionResolver(this));
    }

    @Override
    public MathElementResolver create(MathElement mathElement) {
        Preconditions.checkState(resolvers.containsKey(Preconditions.checkNotNull(mathElement)));

        MathElementResolverCreator resolverCreator = resolvers.get(mathElement);

        return resolverCreator.create();
    }
}
