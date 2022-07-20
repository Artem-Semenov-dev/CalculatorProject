package com.teamdev.bazascript.interpreter.forloop;

import com.teamdev.bazascript.interpreter.runtime.ScriptContext;
import com.teamdev.bazascript.interpreter.util.*;
import com.teamdev.fsm.ExceptionThrower;
import com.teamdev.fsm.FiniteStateMachine;
import com.teamdev.fsm.Transducer;
import com.teamdev.fsm.TransitionMatrix;
import com.teamdev.implementations.type.BooleanValueVisitor;
import com.teamdev.implementations.type.Value;

import java.util.function.BiConsumer;

import static com.teamdev.bazascript.interpreter.forloop.ForLoopStates.*;


public final class ForLoopMachine extends FiniteStateMachine<ForLoopStates, ForLoopContext, ExecutionException> {

    public static ForLoopMachine create(ScriptElementExecutorFactory factory, ExceptionThrower<ExecutionException> exceptionThrower) {
        TransitionMatrix<ForLoopStates> matrix =
                TransitionMatrix.<ForLoopStates>builder()
                        .withStartState(START)
                        .withFinishState(FINISH)

                        .allowTransition(START, FOR_KEYWORD)
                        .allowTransition(FOR_KEYWORD, OPENING_BRACKET)
                        .allowTransition(OPENING_BRACKET, INITIALISE_STATEMENT)
                        .allowTransition(INITIALISE_STATEMENT, FIRST_SEPARATOR)
                        .allowTransition(FIRST_SEPARATOR, CONDITION_STATEMENT)
                        .allowTransition(CONDITION_STATEMENT, SECOND_SEPARATOR)
                        .allowTransition(SECOND_SEPARATOR, UPDATE_VARIABLE_STATEMENT)
                        .allowTransition(UPDATE_VARIABLE_STATEMENT, CLOSING_BRACKET)
                        .allowTransition(CONDITION_STATEMENT, LIST_OF_STATEMENTS)
                        .allowTransition(LIST_OF_STATEMENTS, FINISH)

                        .build();

        return new ForLoopMachine(matrix, factory, exceptionThrower, true);
    }

    public ForLoopMachine(TransitionMatrix<ForLoopStates> matrix, ScriptElementExecutorFactory factory, ExceptionThrower<ExecutionException> exceptionThrower, boolean allowedSkippingWhitespaces) {
        super(matrix, exceptionThrower);

        registerTransducer(START, Transducer.illegalTransition());

        registerTransducer(FINISH, Transducer.autoTransition());

//        registerTransducer(FOR_KEYWORD, new KeywordsTransducer<>("for"));

        registerTransducer(OPENING_BRACKET, Transducer.checkAndPassChar('('));

        registerTransducer(CLOSING_BRACKET, Transducer.checkAndPassChar(')'));

        registerTransducer(FIRST_SEPARATOR, Transducer.checkAndPassChar(';'));

        registerTransducer(SECOND_SEPARATOR, Transducer.checkAndPassChar(';'));

        registerTransducer(LIST_OF_STATEMENTS, new StatementListInBracketsTransducer<>(factory));

        registerTransducer(CONDITION_STATEMENT, new ConditionStatementTransducer(factory));




    }
}