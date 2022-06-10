package src.calculator.math;

import com.google.common.base.Preconditions;
import src.calculator.fsm.DeadlockException;
import src.calculator.fsm.number.NumberStateMachine;

import java.util.EnumMap;
import java.util.Map;
import java.util.Optional;

import static src.calculator.math.MathElement.*;

public class MathElementResolverFactoryImpl implements MathElementResolverFactory {

    private final Map<MathElement, MathElementResolver> resolvers = new EnumMap<>(MathElement.class);

    public MathElementResolverFactoryImpl() {

        resolvers.put(NUMBER, inputChain -> {

            StringBuilder stringBuilder = new StringBuilder(32);

            NumberStateMachine numberMachine = NumberStateMachine.create();

            if (numberMachine.run(inputChain, stringBuilder)){

                return Optional.of(Double.parseDouble(stringBuilder.toString()));
            }


            return Optional.empty();
        });

        resolvers.put(EXPRESSION, new ExpressionResolver(this));

        resolvers.put(OPERAND, new OperandResolver(this));

        resolvers.put(BRACKETS, new BracketsResolver(this));
    }


    @Override
    public MathElementResolver create(MathElement mathElement) {
        Preconditions.checkState(resolvers.containsKey(Preconditions.checkNotNull(mathElement)));

        return resolvers.get(mathElement);
    }
}
