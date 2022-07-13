package com.teamdev.bazascript.interpreter.logicaloperand;

import com.teamdev.bazascript.interpreter.runtime.ScriptContext;
import com.teamdev.bazascript.interpreter.util.ExecutionException;
import com.teamdev.fsm.CharSequenceReader;
import com.teamdev.fsm.Transducer;
import com.teamdev.fsm.identifier.IdentifierMachine;
import com.teamdev.implementations.type.BooleanValueVisitor;
import com.teamdev.implementations.type.Value;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ReadBooleanVariableTransducer implements Transducer<ScriptContext, ExecutionException> {

    private static final Logger logger = LoggerFactory.getLogger(ReadBooleanVariableTransducer.class);

    @Override
    public boolean doTransition(CharSequenceReader inputChain, ScriptContext outputChain) throws ExecutionException {
        StringBuilder variableName = new StringBuilder();

        IdentifierMachine<ExecutionException> nameMachine = IdentifierMachine.create(errorMessage -> {
            throw new ExecutionException(errorMessage);
        });

        int position = inputChain.position();

        if (nameMachine.run(inputChain, variableName)) {

            if (outputChain.hasVariable(variableName.toString())) {

                Value variable = outputChain.memory().getVariable(variableName.toString());

                if (!BooleanValueVisitor.isBoolean(variable)){

                    if (logger.isInfoEnabled()){

                        logger.info("NOT A BOOLEAN VARIABLE INSIDE LOGICAL EXPRESSION");
                    }
                    return false;
                }

                if (outputChain.isParseOnly()) {

                    return true;
                }
                outputChain.systemStack().current().pushOperand(variable);
                return true;
            }
        }
        return false;
    }
}
