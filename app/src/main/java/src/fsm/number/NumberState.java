package src.fsm.number;

import com.google.common.base.Preconditions;
import src.fsm.DeadlockException;
import src.fsm.InputChain;
import src.fsm.Transducer;

public enum NumberState implements Transducer<StringBuilder> {

    START(Transducer.illegalTransition()),

    NEGATIVE_SIGN(new SymbolTransducer('-')),

    INTEGER_DIGIT(new SymbolTransducer(Character::isDigit)),

    DOT(new SymbolTransducer('.')),

    FLOATING_INTEGER(new SymbolTransducer(Character::isDigit)),

    FINISH(Transducer.autoTransition());

    private final Transducer<StringBuilder> origin;

    NumberState(Transducer<StringBuilder> origin) {
        this.origin = Preconditions.checkNotNull(origin);
    }


    @Override
    public boolean doTransition(InputChain inputChain, StringBuilder outputChain) throws DeadlockException {

        return origin.doTransition(inputChain, outputChain);
    }
}
