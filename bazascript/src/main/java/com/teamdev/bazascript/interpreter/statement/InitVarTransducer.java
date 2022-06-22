package com.teamdev.bazascript.interpreter.statement;

import com.teamdev.bazascript.interpreter.ProgramMemory;
import com.teamdev.bazascript.interpreter.initvar.InitVarContext;
import com.teamdev.bazascript.interpreter.initvar.InitVarMachine;
import com.teamdev.calculator.math.MathElementResolverFactory;
import com.teamdev.fsm.CharSequenceReader;
import com.teamdev.fsm.ResolvingException;
import com.teamdev.fsm.Transducer;

public class InitVarTransducer implements Transducer<ProgramMemory> {

    private final MathElementResolverFactory factory;

    InitVarTransducer(MathElementResolverFactory factory) {
        this.factory = factory;
    }


    @Override
    public boolean doTransition(CharSequenceReader inputChain, ProgramMemory outputChain) throws ResolvingException {

        InitVarMachine variableInitMachine = InitVarMachine.create(factory);

        InitVarContext initVarContext = new InitVarContext();

        return variableInitMachine.run(inputChain, initVarContext);
    }
}
