package src.fsm.number;

import com.google.common.base.Preconditions;
import src.fsm.InputChain;
import src.fsm.Transducer;

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
    public boolean doTransition(InputChain inputChain, StringBuilder outputChain) {

        Preconditions.checkNotNull(inputChain, outputChain);

        boolean nextCharIsAvailable = inputChain.hasNext();

        if (nextCharIsAvailable && condition.test(inputChain.currentSymbol())) {

            outputChain.append(inputChain.currentSymbol());

            inputChain.next();

            return true;
        }

        return false;
    }
}
