package com.google.gson.stream;

import static com.google.gson.stream.JsonStreamTokenScope.*;

public class JsonStreamTokenHolder {
    private JsonStreamTokenScope scope = PEEKED_NONE;
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

    public boolean tokenWasPeeked() {
        boolean tokenWasPeeked = scope != PEEKED_NONE;

        return tokenWasPeeked;
    }

    public JsonStreamTokenScope getScope() {
        return scope;
    }
    public void setScope(JsonStreamTokenScope scope) {
        this.scope = scope;
    }

    public boolean hasNext() {
        return scope.hasNextToken();
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
