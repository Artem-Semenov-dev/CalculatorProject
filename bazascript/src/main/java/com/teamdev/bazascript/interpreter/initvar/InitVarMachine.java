package com.teamdev.bazascript.interpreter.initvar;

import com.teamdev.bazascript.interpreter.util.ScriptElement;
import com.teamdev.bazascript.interpreter.util.ScriptElementExecutorFactory;
import com.teamdev.calculator.fsm.function.FunctionNameTransducer;
import com.teamdev.fsm.FiniteStateMachine;
import com.teamdev.fsm.Transducer;
import com.teamdev.fsm.TransitionMatrix;

import static com.teamdev.bazascript.interpreter.initvar.InitVarStates.*;

public final class InitVarMachine extends FiniteStateMachine<InitVarStates, InitVarContext> {

    private InitVarMachine(TransitionMatrix<InitVarStates> matrix, ScriptElementExecutorFactory factory) {
        super(matrix, true);

        registerTransducer(START, Transducer.illegalTransition());
        registerTransducer(ASSIGN, Transducer.checkAndPassChar('='));
        registerTransducer(NAME, new FunctionNameTransducer<>(InitVarContext::setVariableName));
        registerTransducer(EXPRESSION, new VariableExpressionTransducer(factory.create(ScriptElement.EXPRESSION)));
        registerTransducer(FINISH, (inputChain, outputChain) -> {
            outputChain.getContext().memory()
                    .setVariable(outputChain.getVariableName(), outputChain.getVariableValue());

            return true;
        });
    }

    public static InitVarMachine create(ScriptElementExecutorFactory factory) {
        TransitionMatrix<InitVarStates> matrix = TransitionMatrix.<InitVarStates>builder()
                .withStartState(START)
                .withFinishState(FINISH)
                .withTemporaryState(NAME)
                .allowTransition(START, NAME)
                .allowTransition(NAME, ASSIGN)
                .allowTransition(ASSIGN, EXPRESSION)
                .allowTransition(EXPRESSION, FINISH)

                .build();

        return new InitVarMachine(matrix, factory);
    }
}
