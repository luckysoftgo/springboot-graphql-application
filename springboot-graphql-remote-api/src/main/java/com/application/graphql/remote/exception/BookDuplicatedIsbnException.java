package com.application.graphql.remote.exception;

public class BookDuplicatedIsbnException extends RuntimeException {

    public BookDuplicatedIsbnException(String message) {
        super(message);
    }
}
