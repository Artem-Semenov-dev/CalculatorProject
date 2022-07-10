package com.teamdev.bazascript.interpreter.program;

import com.teamdev.bazascript.interpreter.runtime.ScriptContext;
import com.teamdev.bazascript.interpreter.util.ExecutionException;
import com.teamdev.bazascript.interpreter.util.ScriptElement;
import com.teamdev.bazascript.interpreter.util.ScriptElementExecutorFactory;
import com.teamdev.fsm.*;

import static com.teamdev.bazascript.interpreter.program.ProgramStates.*;

/**
 * {@code ProgramMachine} is a realisation of {@link FiniteStateMachine} that used to
 * separate statement and launch machine for them.
 */

public final class ProgramMachine extends FiniteStateMachine<ProgramStates, ScriptContext, ExecutionException> {

    private ProgramMachine(TransitionMatrix<ProgramStates> matrix, ScriptElementExecutorFactory factory,
                           ExceptionThrower<ExecutionException> exceptionThrower) {
        super(matrix, exceptionThrower, true);

        registerTransducer(START, Transducer.illegalTransition());
        registerTransducer(STATEMENT, new StatementTransducer(factory.create(ScriptElement.STATEMENT)));
        registerTransducer(SEPARATOR, Transducer.<ScriptContext, ExecutionException>checkAndPassChar(';').and(
                (inputChain, outputChain) -> {

                    outputChain.memory().updateVariables();

                    return true;
                }
        ));
        registerTransducer(FINISH, Transducer.autoTransition());
    }

    public static ProgramMachine create(ScriptElementExecutorFactory factory, ExceptionThrower<ExecutionException> exceptionThrower) {
        TransitionMatrix<ProgramStates> matrix =
                TransitionMatrix.<ProgramStates>builder()
                        .withStartState(START)
                        .withFinishState(FINISH)
                        .allowTransition(START, STATEMENT)
                        .allowTransition(STATEMENT, SEPARATOR, FINISH)
                        .allowTransition(SEPARATOR, STATEMENT, FINISH)

                        .build();

        return new ProgramMachine(matrix, factory, exceptionThrower);
    }
}
