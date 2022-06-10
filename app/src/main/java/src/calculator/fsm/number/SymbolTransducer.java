package src.calculator.fsm.number;

import com.google.common.base.Preconditions;
import src.calculator.math.CharSequenceReader;
import src.calculator.fsm.Transducer;

import java.util.function.Predicate;

class SymbolTransducer implements Transducer<StringBuilder> {

    private final Predicate<Character> condition;

    SymbolTransducer(Predicate<Character> condition) {

        this.condition = Preconditions.checkNotNull(condition);
    }

    SymbolTransducer(char symbol) {

        this(character -> symbol == character);
    }

    @Override
    public boolean doTransition(CharSequenceReader inputChain, StringBuilder outputChain) {

        Preconditions.checkNotNull(inputChain, outputChain);

        boolean nextCharIsAvailable = inputChain.canRead();

        if (nextCharIsAvailable && condition.test(inputChain.read())) {

            outputChain.append(inputChain.read());

            inputChain.incrementPosition();

            return true;
        }

        return false;
    }
}