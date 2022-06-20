package com.teamdev.bazascript.interpreter.initvar;

import com.teamdev.calculator.math.MathElementResolver;
import com.teamdev.bazascript.interpreter.VariableHolder;
import com.teamdev.fsm.CharSequenceReader;
import com.teamdev.fsm.ResolvingException;
import com.teamdev.fsm.Transducer;

import java.util.Optional;

public class variableExpressionTransducer implements Transducer<VariableHolder> {

    private final MathElementResolver resolver;

    variableExpressionTransducer(MathElementResolver resolver) {
        this.resolver = resolver;
    }

    @Override
    public boolean doTransition(CharSequenceReader inputChain, VariableHolder outputChain) throws ResolvingException {
        Optional<Double> resolve = resolver.resolve(inputChain);
        if (resolve.isPresent()){

            outputChain.putVariable(outputChain.getLastName(), resolve.get());

            return true;
        }
        return false;
    }
}
