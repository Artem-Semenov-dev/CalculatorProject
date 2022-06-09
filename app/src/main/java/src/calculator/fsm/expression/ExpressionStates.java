package src.calculator.fsm.expression;

import com.google.common.base.Preconditions;
import src.calculator.fsm.operand.OperandMachine;
import src.calculator.fsm.DeadlockException;
import src.calculator.math.CharSequenceReader;
import src.calculator.fsm.Transducer;

public enum ExpressionStates {

    START,
    OPERAND,
    BINARY_OPERATOR,
    FINISH
}


