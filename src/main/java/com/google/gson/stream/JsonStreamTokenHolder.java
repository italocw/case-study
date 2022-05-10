package com.google.gson.stream;

public class JsonStreamTokenHolder {
    protected static final int PEEKED_NONE = 0;
    protected static final int PEEKED_BEGIN_OBJECT = 1;
    protected static final int PEEKED_END_OBJECT = 2;
    protected static final int PEEKED_BEGIN_ARRAY = 3;
    protected static final int PEEKED_END_ARRAY = 4;
    protected static final int PEEKED_TRUE = 5;
    protected static final int PEEKED_FALSE = 6;
    protected static final int PEEKED_NULL = 7;
    protected static final int PEEKED_SINGLE_QUOTED = 8;
    protected static final int PEEKED_DOUBLE_QUOTED = 9;
    protected static final int PEEKED_UNQUOTED = 10;
    /**
     * When this is returned, the string value is stored in peekedString.
     */
    protected static final int PEEKED_BUFFERED = 11;
    protected static final int PEEKED_SINGLE_QUOTED_NAME = 12;
    protected static final int PEEKED_DOUBLE_QUOTED_NAME = 13;
    protected static final int PEEKED_UNQUOTED_NAME = 14;
    /**
     * When this is returned, the integer value is stored in peekedLong.
     */
    protected static final int PEEKED_LONG = 15;
    protected static final int PEEKED_NUMBER = 16;
    protected static final int PEEKED_EOF = 17;

    private int type = PEEKED_NONE;
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

    public void clear() {
        type = PEEKED_NONE;
    }

    public boolean tokenWasPeeked() {
        boolean tokenWasPeeked = type != PEEKED_NONE;

        return tokenWasPeeked;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public boolean hasNext() {
        return type != PEEKED_END_OBJECT && type != PEEKED_END_ARRAY && type != PEEKED_EOF;
    }

    public JsonToken toJsonToken() {
        switch (type) {
            case PEEKED_BEGIN_OBJECT:
                return JsonToken.BEGIN_OBJECT;
            case PEEKED_END_OBJECT:
                return JsonToken.END_OBJECT;
            case PEEKED_BEGIN_ARRAY:
                return JsonToken.BEGIN_ARRAY;
            case PEEKED_END_ARRAY:
                return JsonToken.END_ARRAY;
            case PEEKED_SINGLE_QUOTED_NAME:
            case PEEKED_DOUBLE_QUOTED_NAME:
            case PEEKED_UNQUOTED_NAME:
                return JsonToken.NAME;
            case PEEKED_TRUE:
            case PEEKED_FALSE:
                return JsonToken.BOOLEAN;
            case PEEKED_NULL:
                return JsonToken.NULL;
            case PEEKED_SINGLE_QUOTED:
            case PEEKED_DOUBLE_QUOTED:
            case PEEKED_UNQUOTED:
            case PEEKED_BUFFERED:
                return JsonToken.STRING;
            case PEEKED_LONG:
            case PEEKED_NUMBER:
                return JsonToken.NUMBER;
            case PEEKED_EOF:
                return JsonToken.END_DOCUMENT;
            default:
                throw new AssertionError();
        }
    }

    public void setLong(boolean negative, long value) {
        peekedLong = negative ? value : -value;
        type = PEEKED_LONG;
    }

    public void setNumber(int length) {
        peekedNumberLength = length;
        type = PEEKED_NUMBER;

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
