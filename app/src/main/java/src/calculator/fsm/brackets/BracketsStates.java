package src.calculator.fsm.brackets;

import com.google.common.base.Preconditions;
import src.calculator.fsm.DeadlockException;
import src.calculator.fsm.expression.ExpressionMachine;
import src.calculator.fsm.expression.ShuntingYardStack;
import src.calculator.math.CharSequenceReader;
import src.calculator.fsm.Transducer;

public enum BracketsStates {

    START,
    OPENING_BRACKET,
    EXPRESSION,
    CLOSING_BRACKET,
    FINISH
}

