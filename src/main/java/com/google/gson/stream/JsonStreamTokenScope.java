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
        JsonToken jsonToken;

        if (equals(PEEKED_BEGIN_OBJECT)) {
            jsonToken = JsonToken.BEGIN_OBJECT;
        } else if (equals(PEEKED_END_OBJECT)) {
            jsonToken = JsonToken.END_OBJECT;
        } else if (equals(PEEKED_BEGIN_ARRAY)) {
            jsonToken = JsonToken.BEGIN_ARRAY;
        } else if (equals(PEEKED_END_ARRAY)) {
            jsonToken = JsonToken.END_ARRAY;
        } else if (isAName()) {
            jsonToken = JsonToken.NAME;
        } else if (isABoolean()) {
            jsonToken = JsonToken.BOOLEAN;
        } else if (equals(PEEKED_NULL)) {
            jsonToken = JsonToken.NULL;
        } else if (isAString()) {
            jsonToken = JsonToken.STRING;
        } else if (isANumber()) {
            jsonToken = JsonToken.NUMBER;
        } else if (equals(PEEKED_EOF)) {
            jsonToken = JsonToken.END_DOCUMENT;
        } else {
            throw new AssertionError();
        }

        return jsonToken;
    }

    private boolean isANumber() {
        return equals(PEEKED_LONG) || equals(PEEKED_NUMBER);
    }

    private boolean isAString() {
        return equals(PEEKED_SINGLE_QUOTED) || equals(PEEKED_DOUBLE_QUOTED) || equals(PEEKED_UNQUOTED) || equals(PEEKED_BUFFERED);
    }

    private boolean isABoolean() {
        return equals(PEEKED_TRUE) || equals(PEEKED_FALSE);
    }

    private boolean isAName() {
        return equals(PEEKED_SINGLE_QUOTED_NAME) || equals(PEEKED_DOUBLE_QUOTED_NAME) || equals(PEEKED_UNQUOTED_NAME);
    }
}