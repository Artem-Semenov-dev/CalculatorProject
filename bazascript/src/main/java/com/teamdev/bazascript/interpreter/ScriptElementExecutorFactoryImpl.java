package com.teamdev.bazascript.interpreter;

import com.google.common.base.Preconditions;
import com.teamdev.bazascript.interpreter.executors.*;
import com.teamdev.bazascript.interpreter.initvar.InitVarContext;
import com.teamdev.bazascript.interpreter.initvar.InitVarMachine;
import com.teamdev.bazascript.interpreter.initvar.ternary.TernaryOperatorContext;
import com.teamdev.bazascript.interpreter.initvar.ternary.TernaryOperatorMachine;
import com.teamdev.bazascript.interpreter.program.ProgramMachine;
import com.teamdev.bazascript.interpreter.runtime.ScriptContext;
import com.teamdev.bazascript.interpreter.string.StringOperandTransducer;
import com.teamdev.bazascript.interpreter.util.*;
import com.teamdev.bazascript.interpreter.whileoperator.WhileOperatorExecutor;
import com.teamdev.fsm.CharSequenceReader;
import com.teamdev.fsm.ExceptionThrower;
import com.teamdev.fsm.FiniteStateMachine;
import com.teamdev.fsm.Transducer;
import com.teamdev.fsm.identifier.IdentifierMachine;
import com.teamdev.implementations.machines.brackets.BracketsMachine;
import com.teamdev.implementations.machines.expression.ExpressionMachine;
import com.teamdev.implementations.machines.function.FunctionMachine;
import com.teamdev.implementations.machines.number.NumberStateMachine;
import com.teamdev.implementations.operators.AbstractBinaryOperator;
import com.teamdev.implementations.operators.BinaryOperatorFactory;
import com.teamdev.implementations.operators.DoubleBinaryOperatorFactory;
import com.teamdev.implementations.operators.StringBinaryOperatorFactory;
import com.teamdev.implementations.type.DoubleValueVisitor;
import com.teamdev.implementations.type.Value;

import java.util.EnumMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiConsumer;

class ScriptElementExecutorFactoryImpl implements ScriptElementExecutorFactory {

    private final Map<ScriptElement, ScriptElementExecutorCreator> executors = new EnumMap<>(ScriptElement.class);

    ScriptElementExecutorFactoryImpl() {

        BinaryOperatorFactory doubleBiOperatorFactory = new DoubleBinaryOperatorFactory();

        BinaryOperatorFactory stringBiOperatorFactory = new StringBinaryOperatorFactory();

        BiConsumer<ScriptContext, AbstractBinaryOperator> operatorConsumer = (scriptContext, abstractBinaryOperator) -> {
            if (!scriptContext.isParseOnly()) {
                scriptContext.systemStack().current().pushOperator(abstractBinaryOperator);
            }
        };

        ExceptionThrower<ExecutionException> exceptionThrower = errorMessage -> {
            throw new ExecutionException(errorMessage);
        };

        executors.put(ScriptElement.NUMBER, () -> (inputChain, output) -> {

            Optional<Value> execute = NumberStateMachine.execute(inputChain, exceptionThrower);

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
                        operatorConsumer, doubleBiOperatorFactory,
                        new ExecutorProgramElementTransducer(ScriptElement.OPERAND, this).named("Operand").and(
                                (inputChain, outputChain) ->
                                        DoubleValueVisitor.isDouble(outputChain.systemStack().current().peekOperand())
                        ),
                        exceptionThrower)
                )
        );

        executors.put(ScriptElement.STRING_EXPRESSION, () -> new NoSpecialActionExecutor<>(
                ExpressionMachine.create(
                        operatorConsumer, stringBiOperatorFactory,
                        new StringOperandTransducer(this), exceptionThrower
                )
        ));

        executors.put(ScriptElement.RELATIONAL_EXPRESSION, () ->
                new RelationalExpressionElementExecutor(this)
        );

        executors.put(ScriptElement.EXPRESSION, () -> new NoSpecialActionExecutor<>(
                FiniteStateMachine.oneOfMachine(
                        exceptionThrower,
                        new ExecutorProgramElementTransducer(ScriptElement.TERNARY_OPERATOR, this).named("Ternary operator"),
                        new ExecutorProgramElementTransducer(ScriptElement.RELATIONAL_EXPRESSION, this).named("Relational expression"),
                        new ExecutorProgramElementTransducer(ScriptElement.NUMERIC_EXPRESSION, this).named("Numeric expression"),
                        new ExecutorProgramElementTransducer(ScriptElement.STRING_EXPRESSION, this).named("String expression"))
        ));

        executors.put(ScriptElement.OPERAND, () -> new NoSpecialActionExecutor<>(
                FiniteStateMachine.oneOfMachine(
                        exceptionThrower,
                        new ExecutorProgramElementTransducer(ScriptElement.NUMBER, this).named("Number"),
                        new ExecutorProgramElementTransducer(ScriptElement.BRACKETS, this).named("Brackets"),
                        new ExecutorProgramElementTransducer(ScriptElement.FUNCTION, this).named("Function"),
                        new ExecutorProgramElementTransducer(ScriptElement.READ_VARIABLE, this).named("Read variable")
                )
        ));

        executors.put(ScriptElement.BRACKETS, () -> new NoSpecialActionExecutor<>(
                BracketsMachine.create(new ExecutorProgramElementTransducer(ScriptElement.NUMERIC_EXPRESSION, this),
                        exceptionThrower)
        ));

        executors.put(ScriptElement.FUNCTION, () -> new FunctionExecutor(
                new FunctionFactoryExecutor<>(FunctionMachine.create(
                        new FunctionTransducer<>(FunctionHolderWithContext::addArgument, this, ScriptElement.NUMERIC_EXPRESSION)
                                .named("Expression inside function"),
                        FunctionHolderWithContext::setFunctionName,
                        exceptionThrower
                ))
        ));

        executors.put(ScriptElement.INIT_VAR, () -> (inputChain, output) -> {

            InitVarContext initVarContext = new InitVarContext(output);

            InitVarMachine initVarMachine = InitVarMachine.create(this, exceptionThrower);

            return initVarMachine.run(inputChain, initVarContext);
        });

        executors.put(ScriptElement.READ_VARIABLE, () -> (inputChain, context) -> {

            StringBuilder variableName = new StringBuilder();

            IdentifierMachine<ExecutionException> nameMachine = IdentifierMachine.create(exceptionThrower, Character::isLetter);

            if (nameMachine.run(inputChain, variableName)) {

                if (context.hasVariable(variableName.toString())) {

                    if (context.isParseOnly()) {
                        return true;
                    }

                    Value variable = context.memory().getVariable(variableName.toString());

                    context.systemStack().current().pushOperand(variable);
                    return true;
                } else throw new ExecutionException("Not existing variable in memory " + variableName);
            }

            return false;
        });

        executors.put(ScriptElement.PROCEDURE, () -> new FunctionExecutor(
                new ProcedureFactoryExecutor<>(FunctionMachine.create(
                        new FunctionTransducer<>(FunctionHolderWithContext::addArgument, this, ScriptElement.EXPRESSION),
                        FunctionHolderWithContext::setFunctionName,
                        exceptionThrower
                ))
        ));

        executors.put(ScriptElement.STATEMENT, () -> new NoSpecialActionExecutor<>(
                FiniteStateMachine.oneOfMachine(
                        exceptionThrower,
                        new ExecutorProgramElementTransducer(ScriptElement.WHILE_OPERATOR, this).named("While loop"),
                        new ExecutorProgramElementTransducer(ScriptElement.INIT_VAR, this).named("Variable initialisation"),
                        new ExecutorProgramElementTransducer(ScriptElement.PROCEDURE, this).named("Procedure"))
        ));

        executors.put(ScriptElement.PROGRAM, () -> new NoSpecialActionExecutor<>(
                ProgramMachine.create(this, exceptionThrower)
        ));

        executors.put(ScriptElement.WHILE_OPERATOR, () -> new WhileOperatorExecutor(this));

        executors.put(ScriptElement.TERNARY_OPERATOR, () -> (inputChain, output) -> {

            TernaryOperatorMachine ternaryOperatorMachine = TernaryOperatorMachine.create(this, exceptionThrower);

            TernaryOperatorContext ternaryOperatorContext = new TernaryOperatorContext(output);

            return ternaryOperatorMachine.run(inputChain, ternaryOperatorContext);
        });
    }

    @Override
    public ScriptElementExecutor create(ScriptElement element) {

        Preconditions.checkNotNull(element);

        return executors.get(element).create();
    }

}
