package com.teamdev.bazascript.interpreter.operand;

import com.teamdev.bazascript.interpreter.ProgramMemory;
import com.teamdev.calculator.fsm.calculator.DetachedShuntingYardTransducer;
import com.teamdev.calculator.math.MathElement;
import com.teamdev.calculator.math.MathElementResolverFactory;
import com.teamdev.fsm.FiniteStateMachine;
import com.teamdev.fsm.TransitionMatrix;

import java.util.function.BiConsumer;

public class OperandMachine extends FiniteStateMachine<Object, ProgramMemory> {

    public static FiniteStateMachine<Object, ProgramMemory> create(MathElementResolverFactory factory){

        BiConsumer<ProgramMemory, Double> consumer = ProgramMemory::pushOperand;

        return FiniteStateMachine.oneOfMachine(new DetachedShuntingYardTransducer<>(factory.create(MathElement.NUMBER), consumer),
                new DetachedShuntingYardTransducer<>(factory.create(MathElement.BRACKETS), consumer),
                new DetachedShuntingYardTransducer<>(factory.create(MathElement.FUNCTION), consumer),
                new VariableTransducer());
    }

    private OperandMachine(TransitionMatrix<Object> matrix, MathElementResolverFactory factory) {
        super(matrix, true);
    }
}
