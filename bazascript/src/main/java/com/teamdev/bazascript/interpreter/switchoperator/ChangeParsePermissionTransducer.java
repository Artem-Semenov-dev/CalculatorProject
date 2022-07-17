package com.teamdev.bazascript.interpreter.switchoperator;

import com.teamdev.bazascript.interpreter.runtime.WithContext;
import com.teamdev.bazascript.interpreter.util.ExecutionException;
import com.teamdev.fsm.CharSequenceReader;
import com.teamdev.fsm.Transducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * {@code ChangeParsePermissionTransducer} is an implementation of {@link Transducer}
 * that change parse permission in ScriptContext through {@link WithContext} interface.
 * @param <O> output chain that implements {@link WithContext}
 */

class ChangeParsePermissionTransducer<O extends WithContext> implements Transducer<O, ExecutionException> {

    private static final Logger logger = LoggerFactory.getLogger(ChangeParsePermissionTransducer.class);
    
    private final Boolean permission;

    ChangeParsePermissionTransducer(Boolean permission) {
        this.permission = permission;
    }

    @Override
    public boolean doTransition(CharSequenceReader inputChain, O outputChain) throws ExecutionException {

        outputChain.getScriptContext().setParsingPermission(permission);

        if (logger.isInfoEnabled()){

            logger.info("Change parse permission to -> {}", permission);
        }

        return true;
    }
}
