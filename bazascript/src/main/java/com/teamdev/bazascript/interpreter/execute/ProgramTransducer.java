package com.teamdev.bazascript.interpreter.execute;


import com.teamdev.bazascript.interpreter.VariableHolder;
import com.teamdev.bazascript.interpreter.program.ProgramMachine;
import com.teamdev.fsm.CharSequenceReader;
import com.teamdev.fsm.ResolvingException;
import com.teamdev.fsm.Transducer;

public class ProgramTransducer implements Transducer<VariableHolder> {
    @Override
    public boolean doTransition(CharSequenceReader inputChain, VariableHolder outputChain) throws ResolvingException {

        ProgramMachine programMachine = ProgramMachine.create();

        if (programMachine.run(inputChain, outputChain)){

            return true;
        }
        return false;
    }
}
