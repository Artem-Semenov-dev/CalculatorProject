package src.calculator.resolvers;

import src.calculator.fsm.number.NumberStateMachine;
import src.calculator.fsm.util.CharSequenceReader;
import src.calculator.fsm.util.ResolvingException;
import src.calculator.math.MathElementResolver;

import java.util.Optional;

public class NumberResolver implements MathElementResolver {
    @Override
    public Optional<Double> resolve(CharSequenceReader inputChain) throws ResolvingException {

        StringBuilder stringBuilder = new StringBuilder(32);

        NumberStateMachine numberMachine = NumberStateMachine.create();

        if (numberMachine.run(inputChain, stringBuilder)){

            return Optional.of(Double.parseDouble(stringBuilder.toString()));
        }

        return Optional.empty();
    }
}
