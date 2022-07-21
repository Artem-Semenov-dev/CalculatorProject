package com.teamdev.bazascript.interpreter;

import com.google.common.base.Preconditions;
import com.teamdev.bazascript.interpreter.executors.*;
import com.teamdev.bazascript.interpreter.forloop.ForLoopExecutor;
import com.teamdev.bazascript.interpreter.program.ProgramMachine;
import com.teamdev.bazascript.interpreter.util.*;
import com.teamdev.bazascript.interpreter.whileoperator.WhileOperatorExecutor;
import com.teamdev.fsm.FiniteStateMachine;
import com.teamdev.fsm.Transducer;
import com.teamdev.implementations.machines.brackets.BracketsMachine;
import com.teamdev.implementations.machines.expression.ExpressionMachine;
import com.teamdev.implementations.machines.function.FunctionMachine;

import java.util.EnumMap;
import java.util.Map;

class ScriptElementExecutorFactoryImpl implements ScriptElementExecutorFactory {

    private final Map<ScriptElement, ScriptElementExecutorCreator> executors = new EnumMap<>(ScriptElement.class);

    ScriptElementExecutorFactoryImpl() {

        executors.put(ScriptElement.NUMBER, NumberParsingExecutor::new);

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
                        })
                )
        );


        executors.put(ScriptElement.RELATIONAL_EXPRESSION, () ->
                new RelationalExpressionElementExecutor(this)
        );

        executors.put(ScriptElement.EXPRESSION, () -> new NoSpecialActionExecutor<>(
                FiniteStateMachine.oneOfMachine(
                        errorMessage -> {
                            throw new ExecutionException(errorMessage);
                        },
                        new ExecutorProgramElementTransducer(ScriptElement.TERNARY_OPERATOR, this).named("Ternary operator"),
                        new ExecutorProgramElementTransducer(ScriptElement.RELATIONAL_EXPRESSION, this).named("Relational expression"),
                        new ExecutorProgramElementTransducer(ScriptElement.NUMERIC_EXPRESSION, this).named("Numeric expression")
                )
        ));

        executors.put(ScriptElement.OPERAND, () -> new NoSpecialActionExecutor<>(
                FiniteStateMachine.oneOfMachine(
                        errorMessage -> {
                            throw new ExecutionException(errorMessage);
                        },
                        new ExecutorProgramElementTransducer(ScriptElement.NUMBER, this).named("Number operand"),
                        new ExecutorProgramElementTransducer(ScriptElement.BRACKETS, this).named("Brackets operand"),
                        new ExecutorProgramElementTransducer(ScriptElement.FUNCTION, this).named("Function operand"),
                        new ExecutorProgramElementTransducer(ScriptElement.READ_VARIABLE, this).named("Read variable")
                )
        ));

        executors.put(ScriptElement.BRACKETS, () -> new NoSpecialActionExecutor<>(
                BracketsMachine.create(new ExecutorProgramElementTransducer(ScriptElement.NUMERIC_EXPRESSION, this),
                        errorMessage -> {
                            throw new ExecutionException(errorMessage);
                        })
                )
        );

        executors.put(ScriptElement.FUNCTION, () -> new FunctionExecutor(
                new FunctionFactoryExecutor<>(FunctionMachine.create(
                        new FunctionTransducer<>(FunctionHolderWithContext::addArgument, this, ScriptElement.NUMERIC_EXPRESSION)
                                .named("Expression inside function"),
                        Transducer.checkAndPassChar('('),
                        Transducer.checkAndPassChar(')'),
                        FunctionHolderWithContext::setFunctionName,
                        errorMessage -> {
                            throw new ExecutionException(errorMessage);
                        }
                ))
        ));

        executors.put(ScriptElement.INIT_VAR, () -> new InitVarExecutor(this));

        executors.put(ScriptElement.READ_VARIABLE, ReadVariableExecutor::new);

        executors.put(ScriptElement.PROCEDURE, () -> new FunctionExecutor(
                new ProcedureFactoryExecutor<>(FunctionMachine.create(
                        new FunctionTransducer<>(FunctionHolderWithContext::addArgument, this, ScriptElement.EXPRESSION),
                        Transducer.checkAndPassChar('('),
                        Transducer.checkAndPassChar(')'),
                        FunctionHolderWithContext::setFunctionName,
                        errorMessage -> {
                            throw new ExecutionException(errorMessage);
                        }
                ))
        ));

        executors.put(ScriptElement.STATEMENT, () -> new NoSpecialActionExecutor<>(
                FiniteStateMachine.oneOfMachine(
                        errorMessage -> {
                            throw new ExecutionException(errorMessage);
                        },
                        new ExecutorProgramElementTransducer(ScriptElement.WHILE_OPERATOR, this).named("While loop"),
                        new ExecutorProgramElementTransducer(ScriptElement.FOR_LOOP, this).named("For loop"),
                        new ExecutorProgramElementTransducer(ScriptElement.INIT_VAR, this).named("Variable initialisation"),
                        new ExecutorProgramElementTransducer(ScriptElement.PROCEDURE, this).named("Procedure")
                )
        ));

        executors.put(ScriptElement.PROGRAM, () -> new NoSpecialActionExecutor<>(
                ProgramMachine.create(this, errorMessage -> {
                    throw new ExecutionException(errorMessage);
                })
        ));

        executors.put(ScriptElement.WHILE_OPERATOR, () -> new WhileOperatorExecutor(this));

        executors.put(ScriptElement.TERNARY_OPERATOR, () -> new TernaryOperatorExecutor(this));

        executors.put(ScriptElement.FOR_LOOP, () -> new ForLoopExecutor(this));
    }

    @Override
    public ScriptElementExecutor create(ScriptElement element) {

        Preconditions.checkNotNull(element);

        return executors.get(element).create();
    }

}
