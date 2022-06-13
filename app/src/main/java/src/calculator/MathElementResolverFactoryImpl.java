package src.calculator;

import com.google.common.base.Preconditions;
import src.calculator.fsm.brackets.BracketsMachine;
import src.calculator.fsm.operand.OperandMachine;
import src.calculator.math.MathElement;
import src.calculator.math.MathElementResolver;
import src.calculator.math.MathElementResolverFactory;
import src.calculator.resolvers.DetachedShuntingYardResolvers;
import src.calculator.resolvers.ExpressionResolver;
import src.calculator.resolvers.FunctionResolver;
import src.calculator.resolvers.NumberResolver;

import java.util.EnumMap;
import java.util.Map;

import static src.calculator.math.MathElement.*;

public class MathElementResolverFactoryImpl implements MathElementResolverFactory {

    private final Map<MathElement, MathElementResolverCreator> resolvers = new EnumMap<>(MathElement.class);

    MathElementResolverFactoryImpl() {

        resolvers.put(NUMBER, NumberResolver::new);

        resolvers.put(EXPRESSION, () -> new ExpressionResolver(this));

//        resolvers.put(EXPRESSION, () -> new DetachedShuntingYardResolvers<>(ExpressionMachine.create(this)));

        resolvers.put(OPERAND, () -> new DetachedShuntingYardResolvers<>(OperandMachine.create(this)));

        resolvers.put(BRACKETS, () -> new DetachedShuntingYardResolvers<>(BracketsMachine.create(this)));

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
