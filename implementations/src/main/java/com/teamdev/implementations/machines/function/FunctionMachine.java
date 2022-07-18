package com.teamdev.implementations.machines.function;

import com.teamdev.fsm.*;

import java.util.function.BiConsumer;

import static com.teamdev.implementations.machines.function.FunctionStates.*;

/**
 * {@code FunctionMachine} is a realisation of {@link FiniteStateMachine}
 * for parsing a function.
 */

public final class FunctionMachine<O, E extends Exception> extends FiniteStateMachine<FunctionStates, O, E> {

    public static <O, E extends Exception> FunctionMachine<O, E> create(Transducer<O, E> expressionFunctionTransducer,
                                                                        Transducer<O, E> openingSignTransducer,
                                                                        Transducer<O, E> closingSignTransducer,
                                                                        BiConsumer<O, String> biConsumer,
                                                                        ExceptionThrower<E> exceptionThrower){

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

        return new FunctionMachine<>(matrix, expressionFunctionTransducer, openingSignTransducer, closingSignTransducer,
                biConsumer, exceptionThrower);
    }


    private FunctionMachine(TransitionMatrix<FunctionStates> matrix, Transducer<O, E> expressionFunctionTransducer,
                            Transducer<O, E> openingSignTransducer, Transducer<O, E> closingSignTransducer,
                            BiConsumer<O, String> biConsumer, ExceptionThrower<E> exceptionThrower) {
        super(matrix, exceptionThrower, true);

        registerTransducer(START, Transducer.illegalTransition());
        registerTransducer(FINISH, Transducer.autoTransition());
        registerTransducer(OPENING_BRACKET, openingSignTransducer);
        registerTransducer(CLOSING_BRACKET, closingSignTransducer);
        registerTransducer(SEPARATOR, Transducer.checkAndPassChar(','));
        registerTransducer(IDENTIFIER, new NameTransducer<>(biConsumer, exceptionThrower).named("Function name"));
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