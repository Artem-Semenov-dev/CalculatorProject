package com.teamdev.bazascript.interpreter.resolvers;

import com.teamdev.bazascript.interpreter.ProgramMemory;
import com.teamdev.bazascript.interpreter.operand.OperandMachine;
import com.teamdev.calculator.fsm.brackets.BracketsMachine;
import com.teamdev.calculator.math.MathElementResolver;
import com.teamdev.calculator.math.MathElementResolverFactory;
import com.teamdev.fsm.CharSequenceReader;
import com.teamdev.fsm.FiniteStateMachine;
import com.teamdev.fsm.ResolvingException;

import java.util.Optional;

public class OperandResolver implements MathElementResolver {

    private final MathElementResolverFactory factory;

    public OperandResolver(MathElementResolverFactory factory) {
        this.factory = factory;
    }

    @Override
    public Optional<Double> resolve(CharSequenceReader inputChain) throws ResolvingException {

        FiniteStateMachine<Object, ProgramMemory> machine = OperandMachine.create(factory);

        ProgramMemory nestingShuntingYardStack = new ProgramMemory();

        if (machine.run(inputChain, nestingShuntingYardStack)) {

            return Optional.of(nestingShuntingYardStack.peekResult());
        }

        return Optional.empty();
    }
}
