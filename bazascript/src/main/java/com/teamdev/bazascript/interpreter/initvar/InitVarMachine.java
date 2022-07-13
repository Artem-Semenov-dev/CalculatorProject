package com.teamdev.bazascript.interpreter.initvar;

import com.teamdev.bazascript.interpreter.util.ExecutionException;
import com.teamdev.bazascript.interpreter.util.ScriptElement;
import com.teamdev.bazascript.interpreter.util.ScriptElementExecutorFactory;
import com.teamdev.fsm.ExceptionThrower;
import com.teamdev.fsm.FiniteStateMachine;
import com.teamdev.fsm.Transducer;
import com.teamdev.fsm.TransitionMatrix;
import com.teamdev.implementations.machines.function.FunctionNameTransducer;

import static com.teamdev.bazascript.interpreter.initvar.InitVarStates.*;

/**
 * {@code InitVarMachine} is a realisation of {@link FiniteStateMachine} that used to variable initialisation.
 */

public final class InitVarMachine extends FiniteStateMachine<InitVarStates, InitVarContext, ExecutionException> {

    private InitVarMachine(TransitionMatrix<InitVarStates> matrix, ScriptElementExecutorFactory factory,
                           ExceptionThrower<ExecutionException> exceptionThrower) {
        super(matrix, exceptionThrower, true);

        registerTransducer(START, Transducer.illegalTransition());

        registerTransducer(ASSIGN, Transducer.checkAndPassChar('='));

        registerTransducer(NAME, new FunctionNameTransducer<>(InitVarContext::setVariableName,
                errorMessage -> {
                    throw new ExecutionException(errorMessage);
                }).named("Function name"));

        registerTransducer(EXPRESSION, new VariableExpressionTransducer(factory.create(ScriptElement.EXPRESSION)));

        registerTransducer(FINISH, (inputChain, outputChain) -> {

            if(outputChain.isParseOnly()){
                return true;
            }

            outputChain.getScriptContext().memory()
                    .setVariable(outputChain.getVariableName(), outputChain.getVariableValue());

            return true;
        });
    }

    public static InitVarMachine create(ScriptElementExecutorFactory factory, ExceptionThrower<ExecutionException> exceptionThrower) {
        TransitionMatrix<InitVarStates> matrix = TransitionMatrix.<InitVarStates>builder()
                .withStartState(START)
                .withFinishState(FINISH)
                .withTemporaryState(NAME)
                .allowTransition(START, NAME)
                .allowTransition(NAME, ASSIGN)
                .allowTransition(ASSIGN, EXPRESSION)
                .allowTransition(EXPRESSION, FINISH)

                .build();

        return new InitVarMachine(matrix, factory, exceptionThrower);
    }
}
