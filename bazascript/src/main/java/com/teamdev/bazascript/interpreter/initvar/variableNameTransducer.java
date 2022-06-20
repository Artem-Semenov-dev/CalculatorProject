package com.teamdev.bazascript.interpreter.initvar;

import com.teamdev.calculator.fsm.identifier.IdentifierMachine;
import com.teamdev.bazascript.interpreter.VariableHolder;
import com.teamdev.fsm.CharSequenceReader;
import com.teamdev.fsm.ResolvingException;
import com.teamdev.fsm.Transducer;

public class variableNameTransducer implements Transducer<VariableHolder> {
    @Override
    public boolean doTransition(CharSequenceReader inputChain, VariableHolder outputChain) throws ResolvingException {
        StringBuilder stringBuilder = new StringBuilder();

        IdentifierMachine identifierMachine = IdentifierMachine.create();

        if (identifierMachine.run(inputChain, stringBuilder)){

            outputChain.putVariable(stringBuilder.toString(), 0.0);

            outputChain.setLastVariableName(stringBuilder.toString());
            return true;
        }

        return false;
    }
}
