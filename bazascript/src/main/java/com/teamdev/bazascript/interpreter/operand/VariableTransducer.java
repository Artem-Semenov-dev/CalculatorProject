package com.teamdev.bazascript.interpreter.operand;

import com.teamdev.bazascript.interpreter.ProgramMemory;
import com.teamdev.fsm.CharSequenceReader;
import com.teamdev.fsm.ResolvingException;
import com.teamdev.fsm.Transducer;
import com.teamdev.fsm.identifier.IdentifierMachine;

public class VariableTransducer implements Transducer<ProgramMemory> {

    @Override
    public boolean doTransition(CharSequenceReader inputChain, ProgramMemory outputChain) throws ResolvingException {
        StringBuilder stringBuilder = new StringBuilder();

        IdentifierMachine identifierMachine = IdentifierMachine.create();

        if (identifierMachine.run(inputChain, stringBuilder)){

            if (outputChain.hasVariable(stringBuilder.toString())){

                outputChain.pushOperand(outputChain.getVariable(stringBuilder.toString()));
            }

            return true;
        }

        return false;
    }
}
