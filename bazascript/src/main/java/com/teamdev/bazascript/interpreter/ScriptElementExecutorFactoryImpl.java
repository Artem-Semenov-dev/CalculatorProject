package com.teamdev.bazascript.interpreter;

import com.google.common.base.Preconditions;
import com.teamdev.bazascript.interpreter.executors.*;
import com.teamdev.bazascript.interpreter.initvar.InitVarContext;
import com.teamdev.bazascript.interpreter.initvar.InitVarMachine;
import com.teamdev.bazascript.interpreter.initvar.VariableExpressionTransducer;
import com.teamdev.bazascript.interpreter.program.ProgramMachine;
import com.teamdev.bazascript.interpreter.util.*;
import com.teamdev.fsm.FiniteStateMachine;
import com.teamdev.fsm.Transducer;
import com.teamdev.fsm.identifier.IdentifierMachine;
import com.teamdev.implementations.machines.brackets.BracketsMachine;
import com.teamdev.implementations.machines.expression.ExpressionMachine;
import com.teamdev.implementations.machines.function.FunctionMachine;
import com.teamdev.implementations.machines.function.FunctionNameTransducer;
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
                output.systemStack().current().pushOperand(execute.get());

                return true;
            }

            return false;
        });

        executors.put(ScriptElement.EXPRESSION, () ->
                new DetachedShuntingYardExecutor<>(ExpressionMachine.create(
                        (scriptContext, prioritizedBinaryOperator) ->
                                scriptContext.systemStack().current().pushOperator(prioritizedBinaryOperator),
                        new ExecutorProgramElementTransducer(ScriptElement.OPERAND, this),
                        errorMessage -> {
                            throw new ExecutionException(errorMessage);
                        })));

        executors.put(ScriptElement.OPERAND, () -> new NoSpecialActionExecutor<>(
                FiniteStateMachine.oneOfMachine(
                        errorMessage -> {
                            throw new ExecutionException(errorMessage);
                        },
                        new ExecutorProgramElementTransducer(ScriptElement.NUMBER, this),
                        new ExecutorProgramElementTransducer(ScriptElement.BRACKETS, this),
                        new ExecutorProgramElementTransducer(ScriptElement.FUNCTION, this),
                        new ExecutorProgramElementTransducer(ScriptElement.READVARIABLE, this))));

        executors.put(ScriptElement.BRACKETS, () -> new NoSpecialActionExecutor<>(
                BracketsMachine.create(new ExecutorProgramElementTransducer(ScriptElement.EXPRESSION, this),
                        errorMessage -> {
                            throw new ExecutionException(errorMessage);
                        })));

        executors.put(ScriptElement.FUNCTION, () -> new FunctionExecutor(
                new FunctionFactoryExecutor<>(FunctionMachine.create(
                        new FunctionTransducer<>(FunctionHolderWithContext::setArgument, this, ScriptElement.EXPRESSION),
                        FunctionHolderWithContext::setFunctionName,
                        errorMessage -> {
                            throw new ExecutionException(errorMessage);
                        }
                ))));

        executors.put(ScriptElement.INITVAR, () -> (inputChain, output) -> {

            InitVarContext initVarContext = new InitVarContext(output);

            InitVarMachine initVarMachine = InitVarMachine.create(this, errorMessage -> {
                throw new ExecutionException(errorMessage);
            });

            return initVarMachine.run(inputChain, initVarContext);
        });

//        executors.put(ScriptElement.INITVAR, () -> (inputChain, output) -> {
//
//            InitVarContext initVarContext = new InitVarContext(output);
//
//            var initVarMachine =
//                    FiniteStateMachine.chainMachine(errorMessage -> {
//                                throw new ExecutionException(errorMessage);
//                            },
//                            new FunctionNameTransducer<>(InitVarContext::setVariableName,
//                                    errorMessage -> {
//                                        throw new ExecutionException(errorMessage);
//                                    }),
//                            Transducer.checkAndPassChar('='),
//                            new VariableExpressionTransducer(this.create(ScriptElement.EXPRESSION)),
//                            (inputChain1, outputChain) -> {
//                                outputChain.getContext().memory()
//                                        .setVariable(outputChain.getVariableName(), outputChain.getVariableValue());
//                                return true;
//                            });
//
//            return initVarMachine.run(inputChain, initVarContext);
//        });

            executors.put(ScriptElement.READVARIABLE, () -> (inputChain, context) -> {

                StringBuilder variableName = new StringBuilder();

                IdentifierMachine<ExecutionException> nameMachine = IdentifierMachine.create(errorMessage -> {
                    throw new ExecutionException(errorMessage);
                });

                if (nameMachine.run(inputChain, variableName)) {

                    if (context.hasVariable(variableName.toString())) {

                        Value variable = context.memory().getVariable(variableName.toString());

                        context.systemStack().current().pushOperand(variable);
                        return true;
                    } else throw new ExecutionException("Not existing variable in memory " + variableName);
                }
                return false;

            });

            executors.put(ScriptElement.PROCEDURE, () -> new FunctionExecutor(
                    new ProcedureFactoryExecutor<>(FunctionMachine.create(
                            new FunctionTransducer<>(FunctionHolderWithContext::setArgument, this, ScriptElement.EXPRESSION),
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
                            new ExecutorProgramElementTransducer(ScriptElement.INITVAR, this),
                            new ExecutorProgramElementTransducer(ScriptElement.PROCEDURE, this))));

            executors.put(ScriptElement.PROGRAM, () -> new NoSpecialActionExecutor<>(
                    ProgramMachine.create(this, errorMessage -> {
                        throw new ExecutionException(errorMessage);
                    })
            ));
        }

        @Override
        public ScriptElementExecutor create (ScriptElement element){

            Preconditions.checkNotNull(element);

            return executors.get(element).create();
        }

    }
