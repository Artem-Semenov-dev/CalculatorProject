package com.teamdev.bazascript.interpreter.resolvers;

import com.google.common.base.Preconditions;
import com.teamdev.bazascript.interpreter.ProgramMemory;
import com.teamdev.calculator.math.MathElementResolver;
import com.teamdev.fsm.CharSequenceReader;
import com.teamdev.fsm.FiniteStateMachine;
import com.teamdev.fsm.ResolvingException;

import java.util.Optional;

public class DetachedMemoryResolver<I> implements MathElementResolver {

    private final FiniteStateMachine<I, ProgramMemory> machine;

    public DetachedMemoryResolver(FiniteStateMachine<I, ProgramMemory> machine) {
        this.machine = Preconditions.checkNotNull(machine);
    }


    @Override
    public Optional<Double> resolve(CharSequenceReader inputChain) throws ResolvingException {

        ProgramMemory nestingShuntingYardStack = new ProgramMemory();

        if (machine.run(inputChain, nestingShuntingYardStack)) {

            return Optional.of(nestingShuntingYardStack.peekResult());
        }

        return Optional.empty();
    }
}
