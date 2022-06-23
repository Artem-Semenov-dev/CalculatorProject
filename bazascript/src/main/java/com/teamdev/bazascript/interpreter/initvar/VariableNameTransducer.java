package com.teamdev.bazascript.interpreter.initvar;

import com.teamdev.fsm.identifier.IdentifierMachine;
import com.teamdev.fsm.CharSequenceReader;
import com.teamdev.fsm.ResolvingException;
import com.teamdev.fsm.Transducer;

public class VariableNameTransducer implements Transducer<InitVarContext> {
    @Override
    public boolean doTransition(CharSequenceReader inputChain, InitVarContext outputChain) throws ResolvingException {
        StringBuilder stringBuilder = new StringBuilder();

        IdentifierMachine identifierMachine = IdentifierMachine.create();

        if (identifierMachine.run(inputChain, stringBuilder)){

//            outputChain.putVariable(stringBuilder.toString(), 0.0);
//
//            outputChain.setLastVariableName(stringBuilder.toString());

            outputChain.setVariableName(stringBuilder.toString());

            return true;
        }

        return false;
    }
}
