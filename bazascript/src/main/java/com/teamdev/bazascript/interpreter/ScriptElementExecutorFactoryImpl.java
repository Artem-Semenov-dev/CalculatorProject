package com.teamdev.bazascript.interpreter;

import com.google.common.base.Preconditions;
import com.teamdev.bazascript.interpreter.executors.*;
import com.teamdev.bazascript.interpreter.initvar.InitVarContext;
import com.teamdev.bazascript.interpreter.initvar.InitVarMachine;
import com.teamdev.bazascript.interpreter.prefixoperator.UnaryPrefixOperatorExecutor;
import com.teamdev.bazascript.interpreter.program.ProgramMachine;
import com.teamdev.bazascript.interpreter.util.*;
import com.teamdev.bazascript.interpreter.whileoperator.WhileOperatorExecutor;
import com.teamdev.fsm.FiniteStateMachine;
import com.teamdev.implementations.machines.brackets.BracketsMachine;
import com.teamdev.implementations.machines.expression.ExpressionMachine;
import com.teamdev.implementations.machines.function.FunctionMachine;
import com.teamdev.implementations.machines.number.NumberStateMachine;
import com.teamdev.implementations.type.Value;

import java.util.EnumMap;
import java.util.Map;
import java.util.Optional;

class ScriptElementExecutorFactoryImpl implements ScriptElementExecutorFactory {

    private final Map<ScriptElement, ScriptElementExecutorCreator> executors = new EnumMap<>(ScriptElement.class);

    ScriptElementExecutorFactoryImpl() {

        executors.put(ScriptElement.NUMBER, () -> (inputChain, output) -> {

            Optional<Value> execute = NumberStateMachine.execute(inputChain, errorMessage -> {
                throw new ExecutionException(errorMessage);
            });

            if (execute.isPresent()) {

                if (output.isParseOnly()) {
                    return true;
                }

                output.systemStack().current().pushOperand(execute.get());

                return true;
            }

            return false;
        });

        executors.put(ScriptElement.NUMERIC_EXPRESSION, () ->
                new DetachedShuntingYardExecutor<>(ExpressionMachine.create(
                        (scriptContext, abstractBinaryOperator) -> {
                            if (!scriptContext.isParseOnly()) {
                                scriptContext.systemStack().current().pushOperator(abstractBinaryOperator);
                            }
                        },
                        new ExecutorProgramElementTransducer(ScriptElement.OPERAND, this).named("Operand"),
                        errorMessage -> {
                            throw new ExecutionException(errorMessage);
                        })));


        executors.put(ScriptElement.RELATIONAL_EXPRESSION, () ->
                new RelationalExpressionElementExecutor(this));

        executors.put(ScriptElement.EXPRESSION, () -> new NoSpecialActionExecutor<>(
                FiniteStateMachine.oneOfMachine(
                        errorMessage -> {
                            throw new ExecutionException(errorMessage);
                        },
                        new ExecutorProgramElementTransducer(ScriptElement.UNARY_PREFIX_OPERATOR, this).named("Unary Prefix Operator"),
                        new ExecutorProgramElementTransducer(ScriptElement.RELATIONAL_EXPRESSION, this).named("Relational expression"),
                        new ExecutorProgramElementTransducer(ScriptElement.NUMERIC_EXPRESSION, this).named("Numeric expression"))));

        executors.put(ScriptElement.OPERAND, () -> new NoSpecialActionExecutor<>(
                FiniteStateMachine.oneOfMachine(
                        errorMessage -> {
                            throw new ExecutionException(errorMessage);
                        },
                        new ExecutorProgramElementTransducer(ScriptElement.NUMBER, this).named("Number"),
                        new ExecutorProgramElementTransducer(ScriptElement.BRACKETS, this).named("Brackets"),
                        new ExecutorProgramElementTransducer(ScriptElement.FUNCTION, this).named("Function"),
                        new ExecutorProgramElementTransducer(ScriptElement.READ_VARIABLE, this).named("Read variable"))));

        executors.put(ScriptElement.BRACKETS, () -> new NoSpecialActionExecutor<>(
                BracketsMachine.create(new ExecutorProgramElementTransducer(ScriptElement.NUMERIC_EXPRESSION, this)
                                .named("Numeric Expression"),
                        errorMessage -> {
                            throw new ExecutionException(errorMessage);
                        })));

        executors.put(ScriptElement.FUNCTION, () -> new FunctionExecutor(
                new FunctionFactoryExecutor<>(FunctionMachine.create(
                        new FunctionTransducer<>(FunctionHolderWithContext::addArgument, this, ScriptElement.NUMERIC_EXPRESSION)
                                .named("Function"),
                        FunctionHolderWithContext::setFunctionName,
                        errorMessage -> {
                            throw new ExecutionException(errorMessage);
                        }
                ))));

        executors.put(ScriptElement.INIT_VAR, () -> (inputChain, output) -> {

            InitVarContext initVarContext = new InitVarContext(output);

            InitVarMachine initVarMachine = InitVarMachine.create(this, errorMessage -> {
                throw new ExecutionException(errorMessage);
            });

            return initVarMachine.run(inputChain, initVarContext);
        });

        executors.put(ScriptElement.READ_VARIABLE, ReadVariableExecutor::new);

        executors.put(ScriptElement.PROCEDURE, () -> new FunctionExecutor(
                new ProcedureFactoryExecutor<>(FunctionMachine.create(
                        new FunctionTransducer<>(FunctionHolderWithContext::addArgument, this, ScriptElement.EXPRESSION),
                        FunctionHolderWithContext::setFunctionName,
                        errorMessage -> {
                            throw new ExecutionException(errorMessage);
                        }
                ))));

        executors.put(ScriptElement.STATEMENT, () -> new NoSpecialActionExecutor<>(
                FiniteStateMachine.oneOfMachine(
                        errorMessage -> {
                            throw new ExecutionException(errorMessage);
                        },
                        new ExecutorProgramElementTransducer(ScriptElement.INIT_VAR, this).named("Variable initialisation"),
                        new ExecutorProgramElementTransducer(ScriptElement.WHILE_OPERATOR, this).named("While loop"),
                        new ExecutorProgramElementTransducer(ScriptElement.PROCEDURE, this).named("Procedure"),
                        new ExecutorProgramElementTransducer(ScriptElement.UNARY_PREFIX_OPERATOR, this).named("UnaryPrefixOperator"))));

        executors.put(ScriptElement.PROGRAM, () -> new NoSpecialActionExecutor<>(
                ProgramMachine.create(this, errorMessage -> {
                    throw new ExecutionException(errorMessage);
                })
        ));

        executors.put(ScriptElement.WHILE_OPERATOR, () -> new WhileOperatorExecutor(this));


        executors.put(ScriptElement.UNARY_PREFIX_OPERATOR, UnaryPrefixOperatorExecutor::new);
    }

    @Override
    public ScriptElementExecutor create(ScriptElement element) {

        Preconditions.checkNotNull(element);

        return executors.get(element).create();
    }

}
