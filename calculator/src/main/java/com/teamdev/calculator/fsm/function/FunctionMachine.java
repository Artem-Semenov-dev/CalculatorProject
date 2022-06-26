package com.teamdev.calculator.fsm.function;

import com.teamdev.calculator.fsm.util.FunctionHolder;
import com.teamdev.calculator.math.MathElement;
import com.teamdev.calculator.math.MathElementResolverFactory;
import com.teamdev.fsm.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.BiConsumer;

import static com.teamdev.calculator.fsm.function.FunctionStates.*;

/**
 * {@code FunctionMachine} is a realisation of {@link FiniteStateMachine}
 * for parsing a function.
 */

public final class FunctionMachine<O> extends FiniteStateMachine<FunctionStates, O> {

    private final FunctionFactory functionFactory = new FunctionFactory();

    public static <O> FunctionMachine<O> create(Transducer<O> expressionFunctionTransducer, BiConsumer<O, String> biConsumer){

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

        return new FunctionMachine<>(matrix, expressionFunctionTransducer, biConsumer);
    }


    private FunctionMachine(TransitionMatrix<FunctionStates> matrix, Transducer<O> expressionFunctionTransducer,
                            BiConsumer<O, String> biConsumer) {
        super(matrix, true);

        registerTransducer(START, Transducer.illegalTransition());
        registerTransducer(FINISH, Transducer.autoTransition());
        registerTransducer(OPENING_BRACKET, Transducer.checkAndPassChar('('));
        registerTransducer(CLOSING_BRACKET, Transducer.checkAndPassChar(')'));
        registerTransducer(SEPARATOR, Transducer.checkAndPassChar(','));
        registerTransducer(IDENTIFIER, new FunctionNameTransducer<>(biConsumer));
        registerTransducer(EXPRESSION, expressionFunctionTransducer);
    }
}
//new ExpressionFunctionTransducer(factory.create(MathElement.EXPRESSION))
//.and((inputChain, outputChain) -> {
//
//        String functionName = outputChain.getFunctionName();
//
//        if (!functionFactory.hasFunction(functionName)){
//        throw new ResolvingException("Unknown function: " + functionName);
//        }
//
//        return true;
//        })