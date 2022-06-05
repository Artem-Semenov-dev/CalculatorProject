package src.fsm.expression;

import src.fsm.DeadlockException;
import src.fsm.InputChain;
import src.fsm.Transducer;
import src.fsm.number.NumberStateMachine;

public class NumberTransducer implements Transducer<ShuntingYardStack> {

    @Override
    public boolean doTransition(InputChain inputChain, ShuntingYardStack outputChain) throws DeadlockException {
        StringBuilder numberMachineOutput = new StringBuilder();

        NumberStateMachine numberStateMachine = NumberStateMachine.create();

        if (numberStateMachine.run(inputChain, numberMachineOutput)){

            outputChain.pushOperand(Double.parseDouble(numberMachineOutput.toString()));

            return true;
        }
        return false;
    }
}
