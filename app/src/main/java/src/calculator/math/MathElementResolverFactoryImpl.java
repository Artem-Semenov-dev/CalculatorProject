package src.calculator.math;

import com.google.common.base.Preconditions;
import src.calculator.fsm.DeadlockException;
import src.calculator.fsm.Transducer;
import src.calculator.fsm.brackets.BracketsMachine;
import src.calculator.fsm.expression.ExpressionMachine;
import src.calculator.fsm.expression.ShuntingYardStack;
import src.calculator.fsm.number.NumberStateMachine;
import src.calculator.fsm.operand.OperandMachine;

import java.util.EnumMap;
import java.util.Map;
import java.util.Optional;

import static src.calculator.math.MathElement.*;

public class MathElementResolverFactoryImpl implements MathElementResolverFactory {

    private final Map<MathElement, MathElementResolver> resolvers = new EnumMap<>(MathElement.class);

    public MathElementResolverFactoryImpl() {

        resolvers.put(NUMBER, new MathElementResolver() {
            @Override
            public Optional<Double> resolve(CharSequenceReader inputChain) throws DeadlockException {

                StringBuilder stringBuilder = new StringBuilder();

                NumberStateMachine numberMachine = NumberStateMachine.create();

                if (numberMachine.run(inputChain, stringBuilder)){

                    return Optional.of(Double.parseDouble(stringBuilder.toString()));
                }

                return Optional.empty();
            }
        });

        resolvers.put(EXPRESSION, new MathElementResolver() {
            @Override
            public Optional<Double> resolve(CharSequenceReader inputChain) throws ResolvingException, DeadlockException {

                ShuntingYardStack nestingShuntingYardStack = new ShuntingYardStack();

                ExpressionMachine expressionMachine = ExpressionMachine.create();

                if (expressionMachine.run(inputChain, nestingShuntingYardStack)) {

                    return Optional.of(nestingShuntingYardStack.peekResult());
                }

                return Optional.empty();
            }
        });

        resolvers.put(OPERAND, new MathElementResolver() {
            @Override
            public Optional<Double> resolve(CharSequenceReader inputChain) throws ResolvingException, DeadlockException {

                ShuntingYardStack nestingShuntingYardStack = new ShuntingYardStack();

                OperandMachine operandMachine = OperandMachine.create();

                if (operandMachine.run(inputChain, nestingShuntingYardStack)) {

                    return Optional.of(nestingShuntingYardStack.peekResult());
                }

                return Optional.empty();
            }
        });

        resolvers.put(BRACKETS, new MathElementResolver() {
            @Override
            public Optional<Double> resolve(CharSequenceReader inputChain) throws ResolvingException, DeadlockException {

                ShuntingYardStack nestingShuntingYardStack = new ShuntingYardStack();

                BracketsMachine bracketsMachine = BracketsMachine.create();

                if (bracketsMachine.run(inputChain, nestingShuntingYardStack)) {

                    return Optional.of(nestingShuntingYardStack.peekResult());
                }

                return Optional.empty();
            }
        });
    }


    @Override
    public MathElementResolver create(MathElement mathElement) {
        Preconditions.checkState(resolvers.containsKey(Preconditions.checkNotNull(mathElement)));

        return resolvers.get(mathElement);
    }
}
