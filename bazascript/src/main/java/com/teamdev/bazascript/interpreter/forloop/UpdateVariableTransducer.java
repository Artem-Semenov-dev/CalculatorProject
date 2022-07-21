package com.teamdev.bazascript.interpreter.forloop;

import com.teamdev.bazascript.interpreter.util.ExecutionException;
import com.teamdev.bazascript.interpreter.util.ScriptElement;
import com.teamdev.bazascript.interpreter.util.ScriptElementExecutor;
import com.teamdev.bazascript.interpreter.util.ScriptElementExecutorFactory;
import com.teamdev.fsm.CharSequenceReader;
import com.teamdev.fsm.Transducer;

public class UpdateVariableTransducer implements Transducer<ForLoopOutputChain, ExecutionException> {

    private final ScriptElementExecutorFactory factory;

    public UpdateVariableTransducer(ScriptElementExecutorFactory factory) {
        this.factory = factory;
    }

    @Override
    public boolean doTransition(CharSequenceReader inputChain, ForLoopOutputChain outputChain) throws ExecutionException {

        ScriptElementExecutor updateVariableExecutor = factory.create(ScriptElement.INIT_VAR);

        if (updateVariableExecutor.execute(inputChain, outputChain.getScriptContext())){

            if (outputChain.isParseOnly()){

                return true;
            }

            inputChain.setPosition(outputChain.getConditionPosition());

            return true;
        }

        return false;
    }
}
