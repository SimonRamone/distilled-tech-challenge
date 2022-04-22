package org.example.exception;

public class BorrowNotFoundException extends Exception {
    public BorrowNotFoundException(long borrow_id) {
        super(String.format("Borrow not found for id: '%s'", borrow_id));
    }
}