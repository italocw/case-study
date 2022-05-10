package com.google.gson.stream;

import static com.google.gson.stream.JsonStreamValueScope.*;

public class JsonStreamValueHolder {
    private JsonStreamValueScope scope = PEEKED_NONE;
    /**
     * A peeked value that was composed entirely of digits with an optional
     * leading dash. Positive values may not have a leading 0.
     */
    private long peekedLong;

    /**
     * The number of characters in a peeked number literal. Increment 'pos' by
     * this after reading a number.
     */
    private int peekedNumberLength;

    /**
     * A peeked string that should be parsed on the next double, long or string.
     * This is populated before a numeric value is parsed and used if that parsing
     * fails.
     */
    private String peekedString;

    public void dropToken() {
        scope = PEEKED_NONE;
    }

    public boolean hasNone() {
        return scope == PEEKED_NONE;
    }

    public JsonStreamValueScope getScope() {
        return scope;
    }

    public void setScope(JsonStreamValueScope scope) {
        this.scope = scope;
    }

    public boolean hasNext() {
        return scope != PEEKED_END_OBJECT && scope != PEEKED_END_ARRAY && scope != PEEKED_EOF;
    }

    public void setLong(boolean negative, long value) {
        peekedLong = negative ? value : -value;
        scope = PEEKED_LONG;
    }

    public void setNumber(int length) {
        peekedNumberLength = length;
        scope = PEEKED_NUMBER;
    }

    public long getLong() {
        return peekedLong;
    }

    public int getNumberLength() {
        return peekedNumberLength;
    }

    public void setString(String value) {
        peekedString = value;
    }

    public String getString() {
        return peekedString;
    }
}
