package com.teamdev.bazascript.interpreter.util;

import com.teamdev.bazascript.interpreter.runtime.WithContext;
import com.teamdev.fsm.CharSequenceReader;
import com.teamdev.fsm.Transducer;

import java.util.List;

/**
 * {@code KeywordsTransducer} is an implementation of {@link Transducer}
 * that used for read keywords.
 * @param <O> is an output chain that implements {@link WithContext}
 */

public class KeywordsTransducer<O extends WithContext> implements Transducer<O, ExecutionException> {

    private final String keyword;

    public KeywordsTransducer(String keyword) {
        this.keyword = keyword;
    }

    @Override
    public boolean doTransition(CharSequenceReader inputChain, O outputChain) throws ExecutionException {

        List<Transducer<O, ExecutionException>> keyword1 = Transducer.keyword(keyword);

        for (Transducer<O, ExecutionException> transducer : keyword1) {

            if (!transducer.doTransition(inputChain, outputChain)) {
                return false;
            }
        }

        return true;
    }
}
