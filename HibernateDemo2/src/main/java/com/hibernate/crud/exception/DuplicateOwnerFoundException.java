package com.hibernate.crud.exception;

public class DuplicateOwnerFoundException extends RuntimeException {
    public DuplicateOwnerFoundException(String message)
    {
        super(message);
    }
}
