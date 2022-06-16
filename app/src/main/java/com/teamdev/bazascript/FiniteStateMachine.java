package com.teamdev.bazascript;

import com.google.common.base.Preconditions;
import com.teamdev.bazascript.calculator.fsm.util.ResolvingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.teamdev.bazascript.calculator.fsm.util.CharSequenceReader;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

/**
 * {@code FiniteStateMachine} is a realization of concept of
 * <a href = "https://en.wikipedia.org/wiki/Finite-state_machine">Finite state machine</a>
 * <p>
 * It can change states with help of {@link Transducer}
 * </p>
 *
 * <p>
 * Requires a {@link TransitionMatrix} which is a definition of directed graph
 * </p>
 * @param <S> States for {@link TransitionMatrix}
 * @param <O> Output chain
 */

public class FiniteStateMachine<S, O> {

    private static final Logger logger = LoggerFactory.getLogger(FiniteStateMachine.class);

    private final TransitionMatrix<S> matrix;

    private final Map<S, Transducer<O>> transducers = new HashMap<>();

    private final boolean allowedSkippingWhitespaces;

    public FiniteStateMachine(TransitionMatrix<S> matrix, boolean allowedSkippingWhitespaces) {

        this.matrix = Preconditions.checkNotNull(matrix);
        this.allowedSkippingWhitespaces = allowedSkippingWhitespaces;
    }

    protected FiniteStateMachine(TransitionMatrix<S> matrix) {

        this(matrix, false);
    }

    public boolean run(CharSequenceReader inputChain, O outputChain) throws ResolvingException {

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

                throw new ResolvingException("");
            }

            currentState = nextState.get();
        }

        return true;
    }

    private Optional<S> makeNextStep(CharSequenceReader inputChain, O outputChain, S currentState) throws ResolvingException {
        if (allowedSkippingWhitespaces){

            inputChain.skipWhitespaces();
        }
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
