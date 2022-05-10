package com.google.gson.stream;

public enum JsonStreamTokenScope {
    PEEKED_NONE,
    PEEKED_BEGIN_OBJECT,
    PEEKED_END_OBJECT,
    PEEKED_BEGIN_ARRAY,
    PEEKED_END_ARRAY,
    PEEKED_TRUE,
    PEEKED_FALSE,
    PEEKED_NULL,
    PEEKED_SINGLE_QUOTED,
    PEEKED_DOUBLE_QUOTED,
    PEEKED_UNQUOTED,
    PEEKED_BUFFERED,
    PEEKED_SINGLE_QUOTED_NAME,
    PEEKED_DOUBLE_QUOTED_NAME,
    PEEKED_UNQUOTED_NAME,
    PEEKED_LONG,
    PEEKED_NUMBER,
    PEEKED_EOF;

    public boolean hasNextToken() {
        boolean hasNextToken = this != PEEKED_END_OBJECT && this != PEEKED_END_ARRAY && this != PEEKED_EOF;
        return hasNextToken;
    }

    public JsonToken toJsonToken() {
        switch (this) {
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

}
