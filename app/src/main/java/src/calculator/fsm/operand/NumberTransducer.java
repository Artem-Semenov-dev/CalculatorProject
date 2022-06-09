package src.calculator.fsm.operand;

import com.google.common.base.Preconditions;
import src.calculator.fsm.DeadlockException;
import src.calculator.fsm.Transducer;
import src.calculator.fsm.expression.ShuntingYardStack;
import src.calculator.fsm.number.NumberStateMachine;
import src.calculator.math.CharSequenceReader;

public class NumberTransducer implements Transducer<ShuntingYardStack> {

    @Override
    public boolean doTransition(CharSequenceReader inputChain, ShuntingYardStack outputChain) throws DeadlockException {

        Preconditions.checkNotNull(inputChain, outputChain);

        StringBuilder numberMachineOutput = new StringBuilder();

        NumberStateMachine numberStateMachine = NumberStateMachine.create();

        if (numberStateMachine.run(inputChain, numberMachineOutput)) {

            outputChain.pushOperand(Double.parseDouble(numberMachineOutput.toString()));

            return true;
        }
        return false;
    }
}
