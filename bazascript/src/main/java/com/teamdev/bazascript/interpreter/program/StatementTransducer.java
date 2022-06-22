package com.teamdev.bazascript.interpreter.program;

import com.teamdev.bazascript.interpreter.ProgramMemory;
import com.teamdev.bazascript.interpreter.statement.StatementMachine;
import com.teamdev.calculator.math.MathElementResolverFactory;
import com.teamdev.fsm.CharSequenceReader;
import com.teamdev.fsm.ResolvingException;
import com.teamdev.fsm.Transducer;

public class StatementTransducer implements Transducer<ProgramMemory> {

    private final MathElementResolverFactory factory;

    public StatementTransducer(MathElementResolverFactory factory) {
        this.factory = factory;
    }

    @Override
    public boolean doTransition(CharSequenceReader inputChain, ProgramMemory outputChain) throws ResolvingException {

        StatementMachine machine = StatementMachine.create(factory);

        return machine.run(inputChain, outputChain);
    }
}
