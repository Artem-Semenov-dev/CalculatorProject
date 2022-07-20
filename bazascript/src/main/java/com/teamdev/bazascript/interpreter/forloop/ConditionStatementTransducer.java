package com.teamdev.bazascript.interpreter.forloop;

import com.teamdev.bazascript.interpreter.util.ExecutionException;
import com.teamdev.bazascript.interpreter.util.ScriptElement;
import com.teamdev.bazascript.interpreter.util.ScriptElementExecutor;
import com.teamdev.bazascript.interpreter.util.ScriptElementExecutorFactory;
import com.teamdev.fsm.CharSequenceReader;
import com.teamdev.fsm.Transducer;
import com.teamdev.implementations.type.BooleanValueVisitor;
import com.teamdev.implementations.type.Value;

public class ConditionStatementTransducer implements Transducer<ForLoopContext, ExecutionException> {

    private final ScriptElementExecutorFactory factory;

    public ConditionStatementTransducer(ScriptElementExecutorFactory factory) {

        this.factory = factory;
    }

    @Override
    public boolean doTransition(CharSequenceReader inputChain, ForLoopContext outputChain) throws ExecutionException {

        ScriptElementExecutor relationalExecutor = factory.create(ScriptElement.RELATIONAL_EXPRESSION);

        outputChain.setSavedPosition(inputChain.position());

        if (relationalExecutor.execute(inputChain, outputChain.getScriptContext())){

            if (outputChain.getScriptContext().isParseOnly()) {
                return true;
            }

            Value result = outputChain.getScriptContext().systemStack().current().result();

            if (BooleanValueVisitor.isBoolean(result)) {

                outputChain.setConditionValue(BooleanValueVisitor.read(result));

                return true;
            }

            return false;

        }

        return false;
    }
}