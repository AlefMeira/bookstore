package com.project.bookstore.author.exception;

public class AuthorAlreadyExistException extends Exception {
    public AuthorAlreadyExistException(final String name) {
        super(String.format("User with name %s already exists in the system", name));
    }
}
