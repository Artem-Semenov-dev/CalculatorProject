package com.teamdev.bazascript.interpreter.string;

import com.teamdev.bazascript.interpreter.runtime.ScriptContext;
import com.teamdev.bazascript.interpreter.util.ExecutionException;
import com.teamdev.fsm.CharSequenceReader;
import com.teamdev.fsm.Transducer;
import com.teamdev.fsm.identifier.IdentifierMachine;
import com.teamdev.implementations.type.StringValue;

/**
 * {@code StringInnerTransducer} is an implementation of {@link Transducer} that produce string value.
 */

class StringInnerTransducer implements Transducer<ScriptContext, ExecutionException> {

    @Override
    public boolean doTransition(CharSequenceReader inputChain, ScriptContext outputChain) throws ExecutionException {

        StringBuilder stringValue = new StringBuilder();

        IdentifierMachine<ExecutionException> stringReadMachine = IdentifierMachine.create(errorMessage -> {
            throw new ExecutionException(errorMessage);
        }, character -> character != '\'');

        if (stringReadMachine.run(inputChain, stringValue)) {

            if (outputChain.isParseOnly()) {
                return true;
            }

            outputChain.systemStack().current().pushOperand(new StringValue(stringValue.toString()));

            return true;
        }

        return false;
    }
}
