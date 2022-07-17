package com.teamdev.bazascript.interpreter.switchoperator;

import com.teamdev.bazascript.interpreter.util.ExecutionException;
import com.teamdev.bazascript.interpreter.util.KeywordTransducer;
import com.teamdev.bazascript.interpreter.util.ScriptElementExecutorFactory;
import com.teamdev.fsm.ExceptionThrower;
import com.teamdev.fsm.FiniteStateMachine;
import com.teamdev.fsm.Transducer;
import com.teamdev.fsm.TransitionMatrix;

import static com.teamdev.bazascript.interpreter.switchoperator.SwitchStates.*;

public final class SwitchOperatorMachine extends FiniteStateMachine<SwitchStates, SwitchOperatorContext, ExecutionException> {

    private SwitchOperatorMachine(TransitionMatrix<SwitchStates> matrix, ExceptionThrower<ExecutionException> exceptionThrower,
                                  ScriptElementExecutorFactory factory) {
        super(matrix, exceptionThrower, true);

        registerTransducer(START, Transducer.illegalTransition());

        registerTransducer(FINISH, Transducer.autoTransition());

        registerTransducer(SWITCH_KEYWORD, new KeywordTransducer<>("switch"));

        registerTransducer(VARIABLE, new SwitchVariableTransducer(factory));

        registerTransducer(CASE, new KeywordTransducer<>("case"));

        registerTransducer(OPTION, new OptionTransducer(factory));

        registerTransducer(COLON, Transducer.checkAndPassChar(':'));

        registerTransducer(OPENING_BRACE, Transducer.checkAndPassChar('{'));

        registerTransducer(CLOSING_BRACE, Transducer.<SwitchOperatorContext, ExecutionException>checkAndPassChar('}')
                .and(new ChangeParsePermissionTransducer<>(false)));

        registerTransducer(STATEMENT_LIST, new SwitchStatementListTransducer(factory));

        registerTransducer(DEFAULT, new KeywordTransducer<>("default"));
    }

    public static SwitchOperatorMachine create(ScriptElementExecutorFactory factory, ExceptionThrower<ExecutionException> exceptionThrower) {

        TransitionMatrix<SwitchStates> matrix =
                TransitionMatrix.<SwitchStates>builder()
                        .withStartState(START)
                        .withFinishState(FINISH)

                        .allowTransition(START, SWITCH_KEYWORD)
                        .allowTransition(SWITCH_KEYWORD, VARIABLE)
                        .allowTransition(VARIABLE, OPENING_BRACE)
                        .allowTransition(OPENING_BRACE, CASE)
                        .allowTransition(CASE, OPTION)
                        .allowTransition(OPTION, COLON)
                        .allowTransition(COLON, STATEMENT_LIST)
                        .allowTransition(STATEMENT_LIST, CASE, DEFAULT, CLOSING_BRACE)
                        .allowTransition(DEFAULT, COLON)
                        .allowTransition(CLOSING_BRACE, FINISH)

                        .build();

        return new SwitchOperatorMachine(matrix, exceptionThrower, factory);
    }
}
