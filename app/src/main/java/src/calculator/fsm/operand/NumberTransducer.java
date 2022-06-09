package src.calculator.fsm.operand;

import com.google.common.base.Preconditions;
import src.calculator.fsm.DeadlockException;
import src.calculator.fsm.expression.ShuntingYardStack;
import src.calculator.fsm.number.NumberStateMachine;
import src.calculator.fsm.InputChain;
import src.calculator.fsm.Transducer;

public class NumberTransducer implements Transducer<ShuntingYardStack> {

    @Override
    public boolean doTransition(InputChain inputChain, ShuntingYardStack outputChain) throws DeadlockException {

        Preconditions.checkNotNull(inputChain, outputChain);

        StringBuilder numberMachineOutput = new StringBuilder();

        Transducer<StringBuilder> numberStateMachine = NumberStateMachine.create();

        if (numberStateMachine.doTransition(inputChain, numberMachineOutput)) {

            outputChain.pushOperand(Double.parseDouble(numberMachineOutput.toString()));

            return true;
        }
        return false;
    }
}
