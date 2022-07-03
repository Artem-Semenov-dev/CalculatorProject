package com.teamdev.calculator;

import com.google.common.base.Preconditions;
import com.teamdev.calculator.fsm.calculator.DetachedShuntingYardTransducer;
import com.teamdev.calculator.math.MathElement;
import com.teamdev.calculator.math.MathElementResolver;
import com.teamdev.calculator.math.MathElementResolverFactory;
import com.teamdev.calculator.resolvers.DetachedShuntingYardResolver;
import com.teamdev.calculator.resolvers.FunctionResolver;
import com.teamdev.calculator.resolvers.NumberResolver;
import com.teamdev.fsm.FiniteStateMachine;
import com.teamdev.fsm.Transducer;
import com.teamdev.implementations.datastructures.ShuntingYard;
import com.teamdev.implementations.machines.expression.ExpressionMachine;

import java.util.EnumMap;
import java.util.Map;

import static com.teamdev.calculator.math.MathElement.*;

public class MathElementResolverFactoryImpl implements MathElementResolverFactory {

    private final Map<MathElement, MathElementResolverCreator> resolvers = new EnumMap<>(MathElement.class);

    MathElementResolverFactoryImpl() {

        resolvers.put(NUMBER, NumberResolver::new);

        resolvers.put(EXPRESSION, () -> new DetachedShuntingYardResolver<>
                (ExpressionMachine.create(ShuntingYard::pushOperator,
                        new DetachedShuntingYardTransducer<>(OPERAND, ShuntingYard::pushOperand, this),
                        errorMessage -> {
                            throw new ResolvingException(errorMessage);
                        })));

        resolvers.put(OPERAND, () -> new DetachedShuntingYardResolver<>(
                FiniteStateMachine.oneOfMachine(
                        errorMessage -> {
                            throw new ResolvingException(errorMessage);
                        },
                        new DetachedShuntingYardTransducer<>(MathElement.NUMBER, ShuntingYard::pushOperand, this),
                        new DetachedShuntingYardTransducer<>(MathElement.BRACKETS, ShuntingYard::pushOperand, this),
                        new DetachedShuntingYardTransducer<>(MathElement.FUNCTION, ShuntingYard::pushOperand, this))));

//        resolvers.put(BRACKETS, () -> new DetachedShuntingYardResolver<>(BracketsMachine.create(
//                new DetachedShuntingYardTransducer<>(EXPRESSION, ShuntingYard::pushOperand, this), errorMessage -> {
//                    throw new ResolvingException(errorMessage);
//                })));

        resolvers.put(BRACKETS, () -> new DetachedShuntingYardResolver<>(
                FiniteStateMachine.chainMachine(errorMessage -> {
                            throw new ResolvingException(errorMessage);
                        },
                        Transducer.checkAndPassChar('('),
                        new DetachedShuntingYardTransducer<>(EXPRESSION, ShuntingYard::pushOperand, this),
                        Transducer.checkAndPassChar(')')
                )
        ));

        resolvers.put(FUNCTION, () -> new FunctionResolver(this));
    }


    @Override
    public MathElementResolver create(MathElement mathElement) {
        Preconditions.checkState(resolvers.containsKey(Preconditions.checkNotNull(mathElement)));

        MathElementResolverCreator resolverCreator = resolvers.get(mathElement);

        return resolverCreator.create();
    }

}
