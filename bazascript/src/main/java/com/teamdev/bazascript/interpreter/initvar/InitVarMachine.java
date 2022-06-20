package com.teamdev.bazascript.interpreter.initvar;

import com.teamdev.calculator.math.MathElement;
import com.teamdev.calculator.math.MathElementResolverFactory;
import com.teamdev.bazascript.interpreter.VariableHolder;
import com.teamdev.fsm.FiniteStateMachine;
import com.teamdev.fsm.Transducer;
import com.teamdev.fsm.TransitionMatrix;

import static com.teamdev.bazascript.interpreter.initvar.InitVarStates.*;

public final class InitVarMachine extends FiniteStateMachine<InitVarStates, VariableHolder> {

    private InitVarMachine(TransitionMatrix<InitVarStates> matrix, MathElementResolverFactory factory) {
        super(matrix, true);

        registerTransducer(START, Transducer.illegalTransition());
        registerTransducer(ASSIGN, Transducer.checkAndPassChar('='));
        registerTransducer(NAME, new variableNameTransducer());
        registerTransducer(EXPRESSION, new variableExpressionTransducer(factory.create(MathElement.EXPRESSION)));
        registerTransducer(FINISH, Transducer.autoTransition());
    }

    public static InitVarMachine create(MathElementResolverFactory factory){
        TransitionMatrix<InitVarStates> matrix = TransitionMatrix.<InitVarStates>builder()
                .withStartState(START)
                .withFinishState(FINISH)
                .allowTransition(START, NAME)
                .allowTransition(NAME, ASSIGN)
                .allowTransition(ASSIGN, EXPRESSION)
                .allowTransition(EXPRESSION, FINISH)

                .build();

        return new InitVarMachine(matrix, factory);
    }
}
