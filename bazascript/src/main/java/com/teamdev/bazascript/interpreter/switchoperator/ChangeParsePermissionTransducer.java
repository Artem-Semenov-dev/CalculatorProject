package com.teamdev.bazascript.interpreter.switchoperator;

import com.teamdev.bazascript.interpreter.runtime.WithContext;
import com.teamdev.bazascript.interpreter.util.ExecutionException;
import com.teamdev.fsm.CharSequenceReader;
import com.teamdev.fsm.Transducer;

public class ChangeParsePermissionTransducer<O extends WithContext> implements Transducer<O, ExecutionException> {

    private final Boolean permission;

    ChangeParsePermissionTransducer(Boolean permission) {
        this.permission = permission;
    }

    @Override
    public boolean doTransition(CharSequenceReader inputChain, O outputChain) throws ExecutionException {

        outputChain.getScriptContext().setParsingPermission(permission);

        return true;
    }
}
