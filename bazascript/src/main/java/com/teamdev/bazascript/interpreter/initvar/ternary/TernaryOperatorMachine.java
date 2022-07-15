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

/**
 * {@code TernaryOperatorMachine} is a realisation of {@link FiniteStateMachine} that used for parsing and executing ternary operator.
 */

public final class TernaryOperatorMachine extends FiniteStateMachine<TernaryStates, TernaryOperatorContext, ExecutionException> {

    private static final Logger logger = LoggerFactory.getLogger(TernaryOperatorMachine.class);

    private TernaryOperatorMachine(TransitionMatrix<TernaryStates> matrix, ScriptElementExecutorFactory factory, ExceptionThrower<ExecutionException> exceptionThrower) {
        super(matrix, exceptionThrower, true);

        registerTransducer(START, Transducer.illegalTransition());

        registerTransducer(FINISH, Transducer.<TernaryOperatorContext, ExecutionException>autoTransition()
                .and((inputChain, outputChain) -> {

                    if (outputChain.isParseOnly()) {

                        outputChain.getScriptContext().setParsingPermission(false);
                    }

                    return true;
                }));

        registerTransducer(RELATIONAL_EXPRESSION, new RelationalExpressionTransducer(factory.create(ScriptElement.RELATIONAL_EXPRESSION)));

        registerTransducer(QUESTION_MARK, Transducer.<TernaryOperatorContext, ExecutionException>checkAndPassChar('?').and(
                (inputChain, outputChain) -> {
                    if (!outputChain.ternaryOperatorCondition()) {
                        outputChain.getScriptContext().setParsingPermission(true);

                        if (logger.isInfoEnabled()) {

                            logger.info("Set parse permission in ? to - > {}", outputChain.isParseOnly());
                        }
                    }

                    return true;
                }
        ));

        registerTransducer(EXPRESSION, new ExpressionInTernaryOperatorTransducer(factory.create(ScriptElement.EXPRESSION)));

        registerTransducer(COLON, Transducer.<TernaryOperatorContext, ExecutionException>checkAndPassChar(':').and(
                (inputChain, outputChain) -> {

                    if (outputChain.ternaryOperatorCondition()) {

                        outputChain.getScriptContext().setParsingPermission(true);

                        if (logger.isInfoEnabled()) {

                            logger.info("Set parse permission in : to - > {}", outputChain.isParseOnly());
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
                .withTemporaryState(RELATIONAL_EXPRESSION)
                .allowTransition(START, RELATIONAL_EXPRESSION)
                .allowTransition(RELATIONAL_EXPRESSION, QUESTION_MARK)
                .allowTransition(QUESTION_MARK, EXPRESSION)
                .allowTransition(EXPRESSION, COLON)
                .allowTransition(COLON, EXPRESSION)
                .allowTransition(EXPRESSION, FINISH)

                .build();

        return new TernaryOperatorMachine(matrix, factory, exceptionThrower);

    }
}
