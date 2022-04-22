package org.example.exception;

public class BookNotAvailableException extends Exception {
    public BookNotAvailableException(long book_id) {
        super(String.format("Book is not available for id: '%s'", book_id));
    }
}