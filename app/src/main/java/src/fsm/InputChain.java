package src.fsm;

import com.google.common.base.Preconditions;

import java.util.Arrays;

public final class InputChain {

    private final char[] source;

    private int currentPosition;

    public InputChain(String source) {
        this.source = Preconditions.checkNotNull(source).toCharArray();
    }

    public char currentSymbol(){
        return source[currentPosition];
    }

    public void next() {

        currentPosition++;
    }

    public int currentPosition() {
        return currentPosition;
    }

    public boolean hasNext(){

        return currentPosition < source.length;
    }

    public String toString(){
        return Arrays.toString(source);
    }
}
