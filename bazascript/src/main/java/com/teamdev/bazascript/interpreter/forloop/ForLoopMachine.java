package com.teamdev.bazascript.interpreter.forloop;

import com.teamdev.bazascript.interpreter.util.ExecutionException;
import com.teamdev.bazascript.interpreter.util.KeywordsTransducer;
import com.teamdev.bazascript.interpreter.util.ScriptElementExecutorFactory;
import com.teamdev.bazascript.interpreter.util.CodeBlockTransducer;
import com.teamdev.fsm.*;

import static com.teamdev.bazascript.interpreter.forloop.ForLoopStates.*;


public final class ForLoopMachine extends FiniteStateMachine<ForLoopStates, ForLoopOutputChain, ExecutionException> {

    public static ForLoopMachine create(ScriptElementExecutorFactory factory, ExceptionThrower<ExecutionException> exceptionThrower) {
        TransitionMatrix<ForLoopStates> matrix =
                TransitionMatrix.<ForLoopStates>builder()
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

    private ForLoopMachine(TransitionMatrix<ForLoopStates> matrix, ScriptElementExecutorFactory factory, ExceptionThrower<ExecutionException> exceptionThrower, boolean allowedSkippingWhitespaces) {
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

                    if (!outputChain.isParseOnly()){

                        inputChain.setPosition(outputChain.getUpdateVariablePosition());
                    }

                    return true;
                })
        );

    }
}