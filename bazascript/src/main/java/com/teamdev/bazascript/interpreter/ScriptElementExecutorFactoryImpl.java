package com.teamdev.bazascript.interpreter;

import com.google.common.base.Preconditions;
import com.teamdev.bazascript.interpreter.executors.*;
import com.teamdev.bazascript.interpreter.initvar.InitVarContext;
import com.teamdev.bazascript.interpreter.initvar.InitVarMachine;
import com.teamdev.bazascript.interpreter.program.ProgramMachine;
import com.teamdev.bazascript.interpreter.util.*;
import com.teamdev.bazascript.interpreter.whileoperator.WhileOperatorExecutor;
import com.teamdev.fsm.ExceptionThrower;
import com.teamdev.fsm.FiniteStateMachine;
import com.teamdev.implementations.machines.brackets.BracketsMachine;
import com.teamdev.implementations.machines.expression.ExpressionMachine;
import com.teamdev.implementations.machines.function.FunctionMachine;

import java.util.EnumMap;
import java.util.Map;

class ScriptElementExecutorFactoryImpl implements ScriptElementExecutorFactory {

    private final Map<ScriptElement, ScriptElementExecutorCreator> executors = new EnumMap<>(ScriptElement.class);

    ScriptElementExecutorFactoryImpl() {

        ExceptionThrower<ExecutionException> exceptionThrower = errorMessage -> {
            throw new ExecutionException(errorMessage);
        };

        executors.put(ScriptElement.NUMBER, NumberExecutor::new);

        executors.put(ScriptElement.NUMERIC_EXPRESSION, () ->
                new DetachedShuntingYardExecutor<>(ExpressionMachine.create(
                        (scriptContext, abstractBinaryOperator) -> {
                            if (!scriptContext.isParseOnly()) {
                                scriptContext.systemStack().current().pushOperator(abstractBinaryOperator);
                            }
                        },
                        new ExecutorProgramElementTransducer(ScriptElement.OPERAND, this).named("Operand"),
                        exceptionThrower)));

        executors.put(ScriptElement.RELATIONAL_EXPRESSION, () ->
                new RelationalExpressionElementExecutor(this));

        executors.put(ScriptElement.EXPRESSION, () -> new NoSpecialActionExecutor<>(
                FiniteStateMachine.oneOfMachine(
                        exceptionThrower,
                        new ExecutorProgramElementTransducer(ScriptElement.RELATIONAL_EXPRESSION, this).named("Relational expression"),
                        new ExecutorProgramElementTransducer(ScriptElement.NUMERIC_EXPRESSION, this).named("Numeric expression"))));

        executors.put(ScriptElement.OPERAND, () -> new NoSpecialActionExecutor<>(
                FiniteStateMachine.oneOfMachine(
                        exceptionThrower,
                        new ExecutorProgramElementTransducer(ScriptElement.FUNCTION, this).named("Function"),
                        new ExecutorProgramElementTransducer(ScriptElement.BRACKETS, this).named("Brackets"),
                        new ExecutorProgramElementTransducer(ScriptElement.PRODUCE_VARIABLE, this).named("Produce Variable"),
                        new ExecutorProgramElementTransducer(ScriptElement.NUMBER, this).named("Number"))));

        executors.put(ScriptElement.BRACKETS, () -> new NoSpecialActionExecutor<>(
                BracketsMachine.create(
                        new ExecutorProgramElementTransducer(ScriptElement.NUMERIC_EXPRESSION, this).named("Numeric Expression"),
                        exceptionThrower)));

        executors.put(ScriptElement.FUNCTION, () -> new FunctionExecutor(
                new FunctionFactoryExecutor<>(FunctionMachine.create(
                        new FunctionTransducer<>(FunctionHolderWithContext::addArgument, this, ScriptElement.NUMERIC_EXPRESSION)
                                .named("Function"),
                        FunctionHolderWithContext::setFunctionName,
                        exceptionThrower
                ))));

        executors.put(ScriptElement.INIT_VAR, () -> (inputChain, output) -> {

            InitVarContext initVarContext = new InitVarContext(output);

            InitVarMachine initVarMachine = InitVarMachine.create(this, exceptionThrower);

            return initVarMachine.run(inputChain, initVarContext);
        });

        executors.put(ScriptElement.PROCEDURE, () -> new FunctionExecutor(
                new ProcedureFactoryExecutor<>(FunctionMachine.create(
                        new FunctionTransducer<>(FunctionHolderWithContext::addArgument, this, ScriptElement.EXPRESSION),
                        FunctionHolderWithContext::setFunctionName,
                        exceptionThrower
                ))));

        executors.put(ScriptElement.STATEMENT, () -> new NoSpecialActionExecutor<>(
                FiniteStateMachine.oneOfMachine(
                        exceptionThrower,
                        new ExecutorProgramElementTransducer(ScriptElement.INIT_VAR, this).named("Variable initialisation"),
                        new ExecutorProgramElementTransducer(ScriptElement.WHILE_OPERATOR, this).named("While loop"),
                        new ExecutorProgramElementTransducer(ScriptElement.PROCEDURE, this).named("Procedure"),
                        new ExecutorProgramElementTransducer(ScriptElement.PRODUCE_VARIABLE, this).named("UnaryPrefixOperator"))));

        executors.put(ScriptElement.PROGRAM, () -> new NoSpecialActionExecutor<>(
                ProgramMachine.create(this, exceptionThrower)
        ));

        executors.put(ScriptElement.WHILE_OPERATOR, () -> new WhileOperatorExecutor(this));

        executors.put(ScriptElement.PRODUCE_VARIABLE, VariableExecutor::new);
    }

    @Override
    public ScriptElementExecutor create(ScriptElement element) {

        Preconditions.checkNotNull(element);

        return executors.get(element).create();
    }

}
