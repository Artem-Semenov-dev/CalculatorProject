package src.fsm;

import com.google.common.base.Preconditions;

import java.util.*;

public interface TransitionMatrix<S> {

    S getStartState();

    S getFinishState();

    Set<S> getPossibleTransitions(S state);

    static <S> MatrixBuider<S> builder(){
        return new MatrixBuider<>();
    }

    class MatrixBuider<S> {

        private S startState;
        private S finishState;

        private final Map<S, Set<S>> transitions = new TreeMap<>();

        private MatrixBuider() {}

        public MatrixBuider<S> withStartState(S startState){

            Preconditions.checkState(this.startState == null, "Start state is already defined");

            this.startState = Preconditions.checkNotNull(startState);

            return this;
        }

        public MatrixBuider<S> withFinishState(S finishState){

            Preconditions.checkState(this.finishState == null, "Finish state is already defined");

            this.finishState = Preconditions.checkNotNull(finishState);

            return this;
        }

        @SafeVarargs
        public final MatrixBuider<S> allowTransition(S currentState, S... states){

            transitions.put(currentState, new TreeSet<>(Arrays.stream(states).toList()));

            return this;
        }

        public TransitionMatrix<S> build() {
            return new TransitionMatrix<>() {
                @Override
                public S getStartState() {
                    return startState;
                }

                @Override
                public S getFinishState() {
                    return finishState;
                }

                @Override
                public Set<S> getPossibleTransitions(S state) {
                    return transitions.get(state);
                }
            };
        }
    }
}
