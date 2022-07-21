package com.teamdev.bazascript.interpreter.forloop;

import com.teamdev.bazascript.interpreter.util.CodeBlockTransducer;
import com.teamdev.bazascript.interpreter.util.ExecutionException;
import com.teamdev.bazascript.interpreter.util.KeywordsTransducer;
import com.teamdev.bazascript.interpreter.util.ScriptElementExecutorFactory;
import com.teamdev.fsm.ExceptionThrower;
import com.teamdev.fsm.FiniteStateMachine;
import com.teamdev.fsm.Transducer;
import com.teamdev.fsm.TransitionMatrix;

import static com.teamdev.bazascript.interpreter.forloop.ForLoopMachineStates.*;

/**
 * {@code ForLoopMachine} is an implementation of {@link FiniteStateMachine} which is intended to process the for loop.
 * To see information about grammar of for loop and how is it work
 * <a href =https://www.w3schools.com/java/java_for_loop.asp>click here</a>.
 */

final class ForLoopMachine extends FiniteStateMachine<ForLoopMachineStates, ForLoopOutputChain, ExecutionException> {

    private ForLoopMachine(TransitionMatrix<ForLoopMachineStates> matrix, ScriptElementExecutorFactory factory, ExceptionThrower<ExecutionException> exceptionThrower, boolean allowedSkippingWhitespaces) {
        super(matrix, exceptionThrower);

        registerTransducer(START, Transducer.illegalTransition());

        registerTransducer(FINISH, Transducer.<ForLoopOutputChain, ExecutionException>autoTransition()
                .and((inputChain, outputChain) -> {

                    outputChain.prohibitParseOnly();

                    return true;
                })
        );

        registerTransducer(FOR_KEYWORD, new KeywordsTransducer<>("for"));

        registerTransducer(OPENING_BRACKET, Transducer.checkAndPassChar('('));

        registerTransducer(INITIALISE_VARIABLE_STATEMENT, new InitialiseVariableTransducer(factory));

        registerTransducer(FIRST_SEPARATOR, Transducer.<ForLoopOutputChain, ExecutionException>checkAndPassChar(';')
                .and((inputChain, outputChain) -> {

                    outputChain.setConditionPosition(inputChain.position());

                    return true;
                })
        );

        registerTransducer(CONDITION_STATEMENT, new ConditionStatementTransducer(factory));

        registerTransducer(SECOND_SEPARATOR, Transducer.<ForLoopOutputChain, ExecutionException>checkAndPassChar(';'));

        registerTransducer(PARSE_UPDATE_VARIABLE_STATEMENT, new ParseUpdateVariableStatementTransducer(factory));

        registerTransducer(UPDATE_VARIABLE_STATEMENT, new UpdateVariableTransducer(factory));

        registerTransducer(CLOSING_BRACKET, Transducer.checkAndPassChar(')'));

        registerTransducer(LIST_OF_STATEMENTS, new CodeBlockTransducer<ForLoopOutputChain>(factory)
                .and((inputChain, outputChain) -> {

                    if (!outputChain.isParseOnly()) {

                        inputChain.setPosition(outputChain.getUpdateVariablePosition());
                    }

                    return true;
                })
        );

    }

    public static ForLoopMachine create(ScriptElementExecutorFactory factory, ExceptionThrower<ExecutionException> exceptionThrower) {
        TransitionMatrix<ForLoopMachineStates> matrix =
                TransitionMatrix.<ForLoopMachineStates>builder()
                        .withStartState(START)
                        .withFinishState(FINISH)
                        .withTemporaryState(FOR_KEYWORD)

                        .allowTransition(START, FOR_KEYWORD)

                        .allowTransition(FOR_KEYWORD, OPENING_BRACKET)

                        .allowTransition(OPENING_BRACKET, INITIALISE_VARIABLE_STATEMENT)

                        .allowTransition(INITIALISE_VARIABLE_STATEMENT, FIRST_SEPARATOR)

                        .allowTransition(FIRST_SEPARATOR, CONDITION_STATEMENT)

                        .allowTransition(CONDITION_STATEMENT, SECOND_SEPARATOR)

                        .allowTransition(SECOND_SEPARATOR, PARSE_UPDATE_VARIABLE_STATEMENT)

                        .allowTransition(PARSE_UPDATE_VARIABLE_STATEMENT, CLOSING_BRACKET)

                        .allowTransition(CLOSING_BRACKET, LIST_OF_STATEMENTS)

                        .allowTransition(CONDITION_STATEMENT, LIST_OF_STATEMENTS)

                        .allowTransition(LIST_OF_STATEMENTS, UPDATE_VARIABLE_STATEMENT)

                        .allowTransition(UPDATE_VARIABLE_STATEMENT, CONDITION_STATEMENT)

                        .allowTransition(LIST_OF_STATEMENTS, FINISH)

                        .build();

        return new ForLoopMachine(matrix, factory, exceptionThrower, true);
    }
}