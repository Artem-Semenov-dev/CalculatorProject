package src.calculator.math;

import src.calculator.fsm.DeadlockException;
import src.calculator.fsm.expression.ExpressionMachine;
import src.calculator.fsm.expression.ShuntingYardStack;

import java.util.Optional;

public class ExpressionResolver implements MathElementResolver {

    MathElementResolverFactory factory;

    ExpressionResolver(MathElementResolverFactory factory) {
        this.factory = factory;
    }

    @Override
    public Optional<Double> resolve(CharSequenceReader inputChain) throws ResolvingException, DeadlockException {
        ShuntingYardStack nestingShuntingYardStack = new ShuntingYardStack();

        ExpressionMachine expressionMachine = ExpressionMachine.create(factory);

        if (expressionMachine.run(inputChain, nestingShuntingYardStack)) {

            return Optional.of(nestingShuntingYardStack.peekResult());
        }

        return Optional.empty();
    }
}
