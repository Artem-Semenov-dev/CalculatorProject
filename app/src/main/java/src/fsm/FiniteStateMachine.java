package src.fsm;

import com.google.common.base.Preconditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;
import java.util.Set;

public abstract class FiniteStateMachine<S extends Transducer<O>, O> implements Transducer<O> {

    private static final Logger logger = LoggerFactory.getLogger(FiniteStateMachine.class);

    private final TransitionMatrix<S> matrix;


    protected FiniteStateMachine(TransitionMatrix<S> matrix) {

        this.matrix = Preconditions.checkNotNull(matrix);
    }

    @Override
    public boolean doTransition(InputChain inputChain, O outputChain) throws DeadlockException {

        if (logger.isInfoEnabled()) {

            logger.info("Start of machine: {} for {}", getClass().getSimpleName(), inputChain.toString());
        }

        S currentState = matrix.getStartState();

        while (!matrix.getFinishState().equals(currentState)) {

            Optional<S> nextState = makeNextStep(inputChain, outputChain, currentState);

            if (nextState.isEmpty()) {

                if (matrix.getStartState().equals(currentState)) {

                    return false;
                }

                throw new DeadlockException();
            }

            currentState = nextState.get();
        }

        return true;
    }

    private Optional<S> makeNextStep(InputChain inputChain, O outputChain, S currentState) throws DeadlockException {
        Set<S> possibleTransitions = matrix.getPossibleTransitions(currentState);

        for (S potentialState : possibleTransitions) {

            if (potentialState.doTransition(inputChain, outputChain)) {

                if (logger.isInfoEnabled()) {

                    logger.info("Machine on work: {} -> {} on index {}", getClass().getSimpleName(), potentialState, inputChain.currentPosition());
                }

                return Optional.of(potentialState);
            }
        }
        return Optional.empty();
    }
}
