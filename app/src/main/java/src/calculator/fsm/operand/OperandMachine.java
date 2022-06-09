package src.calculator.fsm.operand;

import src.calculator.fsm.TransitionMatrix;
import src.calculator.fsm.FiniteStateMachine;
import src.calculator.fsm.Transducer;
import src.calculator.fsm.brackets.BracketsMachine;
import src.calculator.fsm.expression.ShuntingYardStack;

import static src.calculator.fsm.operand.OperandStates.*;


public final class OperandMachine extends FiniteStateMachine<OperandStates, ShuntingYardStack> {

    public static OperandMachine create() {

        TransitionMatrix<OperandStates> matrix = TransitionMatrix.<OperandStates>builder()
                .withStartState(START)
                .withFinishState(FINISH)
                .allowTransition(START, NUMBER, BRACKETS)
                .allowTransition(NUMBER, FINISH)
                .allowTransition(BRACKETS, FINISH)
                .build();

        return new OperandMachine(matrix);
    }

    private OperandMachine(TransitionMatrix<OperandStates> matrix) {
        super(matrix);

        registerTransducer(START, Transducer.illegalTransition());
        registerTransducer(NUMBER, new NumberTransducer());
        registerTransducer(BRACKETS, new BracketsTransducer());
        registerTransducer(FINISH, Transducer.autoTransition());
    }
}

//    START(Transducer.illegalTransition()),
//        NUMBER(new NumberTransducer()),
//        BRACKETS(Transducer.machineOnNewShuntingYard(BracketsMachine.create())),
//        FINISH(Transducer.autoTransition());
//
//private final Transducer<ShuntingYardStack> origin;
//
//        OperandStates(Transducer<ShuntingYardStack> origin) {
//        this.origin = Preconditions.checkNotNull(origin);
//        }