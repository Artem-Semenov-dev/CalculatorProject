package src.calculator.fsm.util;

import com.google.common.base.Preconditions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * {@code FunctionHolder} is a simple holder class that can be used like an output chain for
 * {@link src.calculator.fsm.identifier.IdentifierMachine} and {@link src.calculator.fsm.expression.ExpressionMachine}.
 * Class can be used for store data to make an instance of {@link com.google.common.base.Function}.
 */

public class FunctionHolder {

    private String functionName;
    private final List<Double> arguments;

    public FunctionHolder() {
        arguments = new ArrayList<>();
    }

    public void setFunctionName(String name){

        this.functionName = Preconditions.checkNotNull(name);
    }

    public void setArgument(Double argument){

        arguments.add(argument);
    }

    public String getFunctionName() {
        return functionName;
    }

    public List<Double> getArguments() {
        return Collections.unmodifiableList(arguments);
    }
}
