package com.project.bookstore.author.exception;

import javax.persistence.EntityExistsException;

public class AuthorAlreadyExistException extends EntityExistsException {
    public AuthorAlreadyExistException(final String name) {
        super(String.format("User with name %s already exists in the system", name));
    }
}
