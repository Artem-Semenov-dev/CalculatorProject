package src.calculator.fsm.util;

import com.google.common.base.Preconditions;

import java.util.Arrays;

/**
 * {@code CharSequenceReader} is a class which can be used to
 * simple work with char array and have a position of reading.
 */

public final class CharSequenceReader {

    private final char[] source;

    private int readingPosition;

    public CharSequenceReader(String source) {
        this.source = Preconditions.checkNotNull(source).toCharArray();
    }

    public char read() {
        return source[readingPosition];
    }

    public void incrementPosition() {

        readingPosition++;
    }

    public int position() {
        return readingPosition;
    }

    public boolean canRead() {

        return readingPosition < source.length;
    }

    @Override
    public String toString() {
        return Arrays.toString(source);
    }

    public void skipWhitespaces() {

        while (canRead() && Character.isWhitespace(read())){

                incrementPosition();
        }
    }
}
