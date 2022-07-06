package com.teamdev.bazascript.interpreter.initvar.ternary;

import com.teamdev.bazascript.interpreter.util.ExecutionException;
import com.teamdev.bazascript.interpreter.util.ScriptElement;
import com.teamdev.bazascript.interpreter.util.ScriptElementExecutorFactory;
import com.teamdev.fsm.ExceptionThrower;
import com.teamdev.fsm.FiniteStateMachine;
import com.teamdev.fsm.Transducer;
import com.teamdev.fsm.TransitionMatrix;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.teamdev.bazascript.interpreter.initvar.ternary.TernaryStates.*;


public final class TernaryOperatorMachine extends FiniteStateMachine<TernaryStates, TernaryOperatorContext, ExecutionException> {

    private static final Logger logger = LoggerFactory.getLogger(TernaryOperatorMachine.class);

    private TernaryOperatorMachine(TransitionMatrix<TernaryStates> matrix, ScriptElementExecutorFactory factory, ExceptionThrower<ExecutionException> exceptionThrower) {
        super(matrix, exceptionThrower, true);


        registerTransducer(START, Transducer.illegalTransition());

        registerTransducer(FINISH, Transducer.<TernaryOperatorContext, ExecutionException>autoTransition()
                .and((inputChain, outputChain) -> {

                    if (outputChain.isParseonly()) {

                        outputChain.getScriptContext().setParsingPermission(false);
                    }

                    return true;
                }));

        registerTransducer(OPENING_BRACKET, Transducer.checkAndPassChar('('));

        registerTransducer(RELATIONAL_EXPRESSION, new RelationalExpressionTransducer(factory.create(ScriptElement.RELATIONAL_EXPRESSION)));

        registerTransducer(CLOSING_BRACKET, Transducer.checkAndPassChar(')'));

        registerTransducer(QUESTION_MARK, Transducer.<TernaryOperatorContext, ExecutionException>checkAndPassChar('?').and(
                (inputChain, outputChain) -> {
                    if (!outputChain.ternaryOperatorCondition()) {
                        outputChain.getScriptContext().setParsingPermission(true);

                        if (logger.isInfoEnabled()) {

                            logger.info("Set parse permission in ? to - > {}", outputChain.isParseonly());
                        }
                    }

                    return true;
                }
        ));

        registerTransducer(EXPRESSION, new ExpressionInTernaryOperatorTransducer(factory.create(ScriptElement.EXPRESSION)));

        registerTransducer(DOUBLE_FLOW, Transducer.<TernaryOperatorContext, ExecutionException>checkAndPassChar(':').and(
                (inputChain, outputChain) -> {

                    if (outputChain.ternaryOperatorCondition()) {

                        outputChain.getScriptContext().setParsingPermission(true);

                        if (logger.isInfoEnabled()) {

                            logger.info("Set parse permission in : to - > {}", outputChain.isParseonly());
                        }
                    } else {

                        outputChain.getScriptContext().setParsingPermission(false);
                    }

                    return true;
                }
        ));
    }

    public static TernaryOperatorMachine create(ScriptElementExecutorFactory factory, ExceptionThrower<ExecutionException> exceptionThrower) {
        TransitionMatrix<TernaryStates> matrix = TransitionMatrix.<TernaryStates>builder()
                .withStartState(START)
                .withFinishState(FINISH)
                .withTemporaryState(OPENING_BRACKET)
                .allowTransition(START, OPENING_BRACKET)
                .allowTransition(OPENING_BRACKET, RELATIONAL_EXPRESSION)
                .allowTransition(RELATIONAL_EXPRESSION, CLOSING_BRACKET)
                .allowTransition(CLOSING_BRACKET, QUESTION_MARK)
                .allowTransition(QUESTION_MARK, OPENING_BRACKET, EXPRESSION)
                .allowTransition(EXPRESSION, DOUBLE_FLOW)
                .allowTransition(DOUBLE_FLOW, OPENING_BRACKET, EXPRESSION)
                .allowTransition(EXPRESSION, FINISH)

                .build();

        return new TernaryOperatorMachine(matrix, factory, exceptionThrower);
    }
}
