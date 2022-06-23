package com.teamdev.bazascript.interpreter.initvar;

import com.teamdev.calculator.math.MathElementResolver;
import com.teamdev.fsm.CharSequenceReader;
import com.teamdev.fsm.ResolvingException;
import com.teamdev.fsm.Transducer;

import java.util.Optional;

public class VariableExpressionTransducer implements Transducer<InitVarContext> {

    private final MathElementResolver resolver;

    VariableExpressionTransducer(MathElementResolver resolver) {
        this.resolver = resolver;
    }

    @Override
    public boolean doTransition(CharSequenceReader inputChain, InitVarContext outputChain) throws ResolvingException {
        Optional<Double> resolve = resolver.resolve(inputChain);
        if (resolve.isPresent()) {

//            outputChain.putVariable(outputChain.getLastName(), resolve.get());

            outputChain.setVariableValue(resolve.get());

            return true;
        }
        return false;
    }
}
