package org.example.exception;


public class AuthorNotFoundException extends Exception {
    public AuthorNotFoundException(long author_id) {
        super(String.format("Author not found for id: '%s'", author_id));
    }

}
