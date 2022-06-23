package com.teamdev.bazascript.interpreter.initvar;

import com.teamdev.calculator.math.MathElement;
import com.teamdev.calculator.math.MathElementResolverFactory;
import com.teamdev.bazascript.interpreter.ProgramMemory;
import com.teamdev.fsm.*;

import static com.teamdev.bazascript.interpreter.initvar.InitVarStates.*;

public final class InitVarMachine extends FiniteStateMachine<InitVarStates, InitVarContext> {

    private InitVarMachine(TransitionMatrix<InitVarStates> matrix, MathElementResolverFactory factory) {
        super(matrix, true);

        registerTransducer(START, Transducer.illegalTransition());
        registerTransducer(ASSIGN, Transducer.checkAndPassChar('='));
        registerTransducer(NAME, new VariableNameTransducer());
        registerTransducer(EXPRESSION, new VariableExpressionTransducer(factory.create(MathElement.EXPRESSION)));
        registerTransducer(FINISH, (inputChain, outputChain) -> {

            ProgramMemory memory = new ProgramMemory();

            memory.putVariable(outputChain.getVariableName(), outputChain.getVariableValue());

            return true;
        });
    }

    public static InitVarMachine create(MathElementResolverFactory factory){
        TransitionMatrix<InitVarStates> matrix = TransitionMatrix.<InitVarStates>builder()
                .withStartState(START)
                .withFinishState(FINISH)
                .withTemporaryState(NAME)
                .allowTransition(START, NAME)
                .allowTransition(NAME, ASSIGN)
                .allowTransition(ASSIGN, EXPRESSION)
                .allowTransition(EXPRESSION, FINISH)

                .build();

        return new InitVarMachine(matrix, factory);
    }
}
