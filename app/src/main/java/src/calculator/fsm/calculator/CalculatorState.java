package src.calculator.fsm.calculator;

import com.google.common.base.Preconditions;
import src.calculator.fsm.DeadlockException;
import src.calculator.fsm.expression.ShuntingYardStack;
import src.calculator.math.CharSequenceReader;
import src.calculator.fsm.Transducer;
import src.calculator.fsm.expression.ExpressionMachine;

enum CalculatorState {

    START,
    EXPRESSION,
    FINISH

}


