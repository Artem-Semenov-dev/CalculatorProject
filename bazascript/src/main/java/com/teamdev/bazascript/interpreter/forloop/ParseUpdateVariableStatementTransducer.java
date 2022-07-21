package com.teamdev.bazascript.interpreter.forloop;

import com.teamdev.bazascript.interpreter.util.ExecutionException;
import com.teamdev.bazascript.interpreter.util.ScriptElement;
import com.teamdev.bazascript.interpreter.util.ScriptElementExecutor;
import com.teamdev.bazascript.interpreter.util.ScriptElementExecutorFactory;
import com.teamdev.fsm.CharSequenceReader;
import com.teamdev.fsm.Transducer;

public class ParseUpdateVariableStatementTransducer implements Transducer<ForLoopOutputChain, ExecutionException> {

    private final ScriptElementExecutorFactory factory;

    ParseUpdateVariableStatementTransducer(ScriptElementExecutorFactory factory) {
        this.factory = factory;
    }

    @Override
    public boolean doTransition(CharSequenceReader inputChain, ForLoopOutputChain outputChain) throws ExecutionException {

        outputChain.parseOnly();

        ScriptElementExecutor updateVariableExecutor = factory.create(ScriptElement.INIT_VAR);

        outputChain.setUpdateVariablePosition(inputChain.position());

        if (updateVariableExecutor.execute(inputChain, outputChain.getScriptContext())){

            if (outputChain.getConditionValue()) {

                outputChain.prohibitParseOnly();
            }

            return true;
        }

        return false;
    }
}
