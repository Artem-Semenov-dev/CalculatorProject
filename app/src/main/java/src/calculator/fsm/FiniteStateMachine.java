package src.calculator.fsm;

import com.google.common.base.Preconditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import src.calculator.math.CharSequenceReader;
import src.calculator.math.ResolvingException;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public class FiniteStateMachine<S, O> {

    private static final Logger logger = LoggerFactory.getLogger(FiniteStateMachine.class);

    private final TransitionMatrix<S> matrix;

    private final Map<S, Transducer<O>> transducers = new HashMap<>();

    protected FiniteStateMachine(TransitionMatrix<S> matrix) {

        this.matrix = Preconditions.checkNotNull(matrix);
    }

    public boolean run(CharSequenceReader inputChain, O outputChain) throws DeadlockException, ResolvingException {

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

    private Optional<S> makeNextStep(CharSequenceReader inputChain, O outputChain, S currentState) throws DeadlockException, ResolvingException {
        Set<S> possibleTransitions = matrix.getPossibleTransitions(currentState);

        for (S potentialState : possibleTransitions) {

            Transducer<O> transducer = transducers.get(potentialState);

            if (transducer.doTransition(inputChain, outputChain)) {

                if (logger.isInfoEnabled()) {

                    logger.info("Machine on work: {} -> {} on index {}", getClass().getSimpleName(), potentialState, inputChain.position());
                }

                return Optional.of(potentialState);
            }
        }
        return Optional.empty();
    }

    protected void registerTransducer(S state, Transducer<O> transducer){

        transducers.put(state, transducer);
    }
}
