package com.teamdev.bazascript.interpreter.statement;

import com.teamdev.bazascript.interpreter.ProgramMemory;
import com.teamdev.bazascript.interpreter.procedure.Procedure;
import com.teamdev.bazascript.interpreter.procedure.ProcedureFactory;
import com.teamdev.calculator.fsm.function.FunctionMachine;
import com.teamdev.calculator.fsm.util.FunctionHolder;
import com.teamdev.calculator.math.MathElementResolverFactory;
import com.teamdev.fsm.CharSequenceReader;
import com.teamdev.fsm.ResolvingException;
import com.teamdev.fsm.Transducer;

public class ProcedureTransducer implements Transducer<ProgramMemory> {

    private final MathElementResolverFactory factory;

    ProcedureTransducer(MathElementResolverFactory factory) {
        this.factory = factory;
    }

    @Override
    public boolean doTransition(CharSequenceReader inputChain, ProgramMemory outputChain) throws ResolvingException {

        FunctionHolder functionHolder = new FunctionHolder();

        ProcedureFactory procedureFactory = new ProcedureFactory();

        FunctionMachine machine = FunctionMachine.create(factory);

        if (machine.run(inputChain, functionHolder)){

            Procedure procedure = procedureFactory.create(functionHolder.getFunctionName());

            procedure.create(functionHolder.getArguments(), outputChain);

            return true;
        }

        return false;
    }
}
