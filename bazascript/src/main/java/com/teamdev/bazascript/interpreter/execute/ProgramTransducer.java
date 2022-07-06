package com.teamdev.bazascript.interpreter.execute;


import com.google.common.base.Preconditions;
import com.teamdev.bazascript.interpreter.runtime.ScriptContext;
import com.teamdev.bazascript.interpreter.util.ExecutionException;
import com.teamdev.bazascript.interpreter.util.ScriptElementExecutor;
import com.teamdev.fsm.CharSequenceReader;
import com.teamdev.fsm.Transducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProgramTransducer implements Transducer<ScriptContext, ExecutionException> {

    private static final Logger logger = LoggerFactory.getLogger(ProgramTransducer.class);

    private final ScriptElementExecutor executor;

    public ProgramTransducer(ScriptElementExecutor executor) {

        this.executor = Preconditions.checkNotNull(executor);
    }


    @Override
    public boolean doTransition(CharSequenceReader inputChain, ScriptContext outputChain) throws ExecutionException {

        Preconditions.checkNotNull(inputChain, outputChain);

        if (logger.isInfoEnabled()) {

            logger.info("Working with input chain -> {}", inputChain);

            logger.info("OutputChain is null : {}", (outputChain));
        }

        return executor.execute(inputChain, outputChain);
    }
}
