package com.teamdev.bazascript.calculator.resolvers;

import com.google.common.base.Preconditions;
import com.teamdev.bazascript.FiniteStateMachine;
import com.teamdev.bazascript.calculator.fsm.util.CharSequenceReader;
import com.teamdev.bazascript.calculator.fsm.util.ResolvingException;
import com.teamdev.bazascript.calculator.fsm.util.ShuntingYardStack;
import com.teamdev.bazascript.calculator.math.MathElementResolver;

import java.util.Optional;

/**
 * {@code DetachedShuntingYardResolver} is a universal implementation of {@link MathElementResolver}
 * that can be used for resolve input chain for {@link FiniteStateMachine}
 * which work based on new instances of {@link ShuntingYardStack}.
 * @param <I> input chain for state machine.
 */

public class DetachedShuntingYardResolver<I> implements MathElementResolver {

    private final FiniteStateMachine<I, ShuntingYardStack> machine;

    public DetachedShuntingYardResolver(FiniteStateMachine<I, ShuntingYardStack> machine) {
        this.machine = Preconditions.checkNotNull(machine);
    }


    @Override
    public Optional<Double> resolve(CharSequenceReader inputChain) throws ResolvingException {

        ShuntingYardStack nestingShuntingYardStack = new ShuntingYardStack();

        if (machine.run(inputChain, nestingShuntingYardStack)) {

            return Optional.of(nestingShuntingYardStack.peekResult());
        }

        return Optional.empty();
    }
}
