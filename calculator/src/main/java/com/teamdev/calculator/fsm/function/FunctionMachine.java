package com.teamdev.calculator.fsm.function;

import com.teamdev.calculator.fsm.util.FunctionHolder;
import com.teamdev.calculator.math.MathElement;
import com.teamdev.calculator.math.MathElementResolverFactory;
import com.teamdev.fsm.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.teamdev.calculator.fsm.function.FunctionStates.*;

/**
 * {@code FunctionMachine} is a realisation of {@link FiniteStateMachine}
 * for parsing a function.
 */

public final class FunctionMachine extends FiniteStateMachine<FunctionStates, FunctionHolder> {

    FunctionFactory functionFactory = new FunctionFactory();

    public static FunctionMachine create(MathElementResolverFactory factory){

        TransitionMatrix<FunctionStates> matrix = TransitionMatrix.<FunctionStates>builder()
                .withStartState(START)
                .withFinishState(FINISH)
                .withTemporaryState(IDENTIFIER)
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
        registerTransducer(OPENING_BRACKET, Transducer.<FunctionHolder>checkAndPassChar('(').and((inputChain, outputChain) -> {

            String functionName = outputChain.getFunctionName();

            if (!functionFactory.hasFunction(functionName)){
                throw new ResolvingException("Unknown function: " + functionName);
            }

            return true;
        }));
        registerTransducer(CLOSING_BRACKET, Transducer.checkAndPassChar(')'));
        registerTransducer(SEPARATOR, Transducer.checkAndPassChar(','));
        registerTransducer(IDENTIFIER, new FunctionNameTransducer());
        registerTransducer(EXPRESSION, new ExpressionFunctionTransducer(factory.create(MathElement.EXPRESSION)));
    }
}
