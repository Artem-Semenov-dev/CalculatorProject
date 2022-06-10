package src.calculator.math;

import src.calculator.fsm.DeadlockException;
import src.calculator.fsm.expression.ShuntingYardStack;
import src.calculator.fsm.operand.OperandMachine;

import java.util.Optional;

public class OperandResolver implements MathElementResolver {

    MathElementResolverFactory factory;

    OperandResolver(MathElementResolverFactory factory) {
        this.factory = factory;
    }

    @Override
    public Optional<Double> resolve(CharSequenceReader inputChain) throws ResolvingException, DeadlockException {
        ShuntingYardStack nestingShuntingYardStack = new ShuntingYardStack();

        OperandMachine operandMachine = OperandMachine.create(factory);

        if (operandMachine.run(inputChain, nestingShuntingYardStack)) {

            return Optional.of(nestingShuntingYardStack.peekResult());
        }

        return Optional.empty();
    }
}
