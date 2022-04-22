package org.example.exception;

public class BookNotFoundException extends Exception {
    public BookNotFoundException(long book_id) {
        super(String.format("Book not found for id: '%s'", book_id));
    }
}