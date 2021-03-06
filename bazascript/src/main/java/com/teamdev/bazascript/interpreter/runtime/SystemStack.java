package com.teamdev.bazascript.interpreter.runtime;

import com.google.common.base.Preconditions;
import com.teamdev.implementations.datastructures.ShuntingYard;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * {@code SystemStack} is a stack of {@link ShuntingYard} it is used to store results of calculations and value of variables.
 */

public class SystemStack {

    private final Deque<ShuntingYard> stacks = new ArrayDeque<>();

    public void create() {

        stacks.push(new ShuntingYard());
    }

    public ShuntingYard current() {

        Preconditions.checkState(stacks.size() > 0);

        return stacks.peek();
    }

    public ShuntingYard close() {

        Preconditions.checkState(stacks.size() > 0);

        return stacks.pop();
    }

//    private final Deque<Value> stack = new ArrayDeque<>();
//
//    public void pushValue(Value value){
//
//        stack.push(value);
//    }
//
//    public Value popValue(){
//
//        return stack.pop();
//    }
}
