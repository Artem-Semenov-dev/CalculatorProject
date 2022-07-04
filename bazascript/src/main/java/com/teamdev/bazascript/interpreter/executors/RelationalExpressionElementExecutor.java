package com.teamdev.bazascript.interpreter.executors;

import com.teamdev.bazascript.interpreter.ExecutorProgramElementTransducer;
import com.teamdev.bazascript.interpreter.runtime.ScriptContext;
import com.teamdev.bazascript.interpreter.util.ExecutionException;
import com.teamdev.bazascript.interpreter.util.ScriptElement;
import com.teamdev.bazascript.interpreter.util.ScriptElementExecutor;
import com.teamdev.bazascript.interpreter.util.ScriptElementExecutorFactory;
import com.teamdev.fsm.CharSequenceReader;
import com.teamdev.fsm.FiniteStateMachine;
import com.teamdev.implementations.machines.expression.BinaryOperatorTransducer;
import com.teamdev.implementations.operators.BinaryOperatorFactory;
import com.teamdev.implementations.operators.RelationalBinaryOperatorFactory;

import java.util.List;

public class RelationalExpressionElementExecutor implements ScriptElementExecutor {

    private final ScriptElementExecutorFactory factory;

    public RelationalExpressionElementExecutor(ScriptElementExecutorFactory scriptElementExecutorFactory) {
        this.factory = scriptElementExecutorFactory;
    }

    @Override
    public boolean execute(CharSequenceReader inputChain, ScriptContext output) throws ExecutionException {

        BinaryOperatorFactory relationalOperatorFactory = new RelationalBinaryOperatorFactory();

        var partOfExpression = new ExecutorProgramElementTransducer(ScriptElement.NUMERIC_EXPRESSION, factory)
                .named("Part Of Relational Expression");

        var relationalMachine = FiniteStateMachine.chainMachine(errorMessage -> {
                    throw new ExecutionException(errorMessage);
                },
                List.of(partOfExpression),

                List.of(partOfExpression,
                        new BinaryOperatorTransducer<>(relationalOperatorFactory,
                                (scriptContext, abstractBinaryOperator) -> {
                                    if (!scriptContext.isParseonly()) {
                                        scriptContext.systemStack().current().pushOperator(abstractBinaryOperator);
                                    }
                                }),
                        partOfExpression));

        return relationalMachine.run(inputChain, output);
    }
}
