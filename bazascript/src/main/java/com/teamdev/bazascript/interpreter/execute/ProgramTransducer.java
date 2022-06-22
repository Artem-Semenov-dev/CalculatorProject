package com.teamdev.bazascript.interpreter.execute;


import com.teamdev.bazascript.interpreter.ProgramMemory;
import com.teamdev.bazascript.interpreter.program.ProgramMachine;
import com.teamdev.calculator.math.MathElementResolverFactory;
import com.teamdev.fsm.CharSequenceReader;
import com.teamdev.fsm.ResolvingException;
import com.teamdev.fsm.Transducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProgramTransducer implements Transducer<ProgramMemory> {

    private static final Logger logger = LoggerFactory.getLogger(ProgramTransducer.class);

    private final MathElementResolverFactory factory;

    public ProgramTransducer(MathElementResolverFactory factory) {

        this.factory = factory;
    }


    @Override
    public boolean doTransition(CharSequenceReader inputChain, ProgramMemory outputChain) throws ResolvingException {

        ProgramMachine programMachine = ProgramMachine.create(factory);

        if (logger.isInfoEnabled()){

            logger.info("Working with input chain -> {}", inputChain.toString());
        }

        return programMachine.run(inputChain, outputChain);
    }
}
