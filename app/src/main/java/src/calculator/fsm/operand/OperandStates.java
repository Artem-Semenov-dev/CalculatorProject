package src.calculator.fsm.operand;

import com.google.common.base.Preconditions;
import src.calculator.fsm.DeadlockException;
import src.calculator.math.CharSequenceReader;
import src.calculator.fsm.Transducer;
import src.calculator.fsm.brackets.BracketsMachine;
import src.calculator.fsm.expression.ShuntingYardStack;

public enum OperandStates {

    START,
    NUMBER,
    BRACKETS,
    FINISH
}


