package com.teamdev.bazascript.calculator.fsm.function;

import com.teamdev.bazascript.FiniteStateMachine;
import com.teamdev.bazascript.Transducer;
import com.teamdev.bazascript.TransitionMatrix;
import com.teamdev.bazascript.calculator.fsm.util.FunctionHolder;
import com.teamdev.bazascript.calculator.math.MathElement;
import com.teamdev.bazascript.calculator.math.MathElementResolverFactory;

import static com.teamdev.bazascript.calculator.fsm.function.FunctionStates.*;

/**
 * {@code FunctionMachine} is a realisation of {@link FiniteStateMachine}
 * for parsing a function.
 */

public final class FunctionMachine extends FiniteStateMachine<FunctionStates, FunctionHolder> {

    public static FunctionMachine create(MathElementResolverFactory factory){

        TransitionMatrix<FunctionStates> matrix = TransitionMatrix.<FunctionStates>builder()
                .withStartState(START)
                .withFinishState(FINISH)
                .allowTransition(START, IDENTIFIER)
                .allowTransition(IDENTIFIER, OPENING_BRACKET)
                .allowTransition(OPENING_BRACKET, CLOSING_BRACKET, EXPRESSION)
                .allowTransition(EXPRESSION, SEPARATOR, CLOSING_BRACKET)
                .allowTransition(SEPARATOR, EXPRESSION)
                .allowTransition(CLOSING_BRACKET, FINISH)

                .build();

        return new FunctionMachine(matrix, factory);
    }


    private FunctionMachine(TransitionMatrix<FunctionStates> matrix, MathElementResolverFactory factory) {
        super(matrix, true);

        registerTransducer(START, Transducer.illegalTransition());
        registerTransducer(FINISH, Transducer.autoTransition());
        registerTransducer(OPENING_BRACKET, Transducer.checkAndPassChar('('));
        registerTransducer(CLOSING_BRACKET, Transducer.checkAndPassChar(')'));
        registerTransducer(SEPARATOR, Transducer.checkAndPassChar(','));
        registerTransducer(IDENTIFIER, new FunctionNameTransducer());
        registerTransducer(EXPRESSION, new ExpressionFunctionTransducer(factory.create(MathElement.EXPRESSION)));
    }
}
