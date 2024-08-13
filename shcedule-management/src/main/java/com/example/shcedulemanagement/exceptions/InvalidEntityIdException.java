package com.example.shcedulemanagement.exceptions;

public class InvalidEntityIdException extends RuntimeException {
    public InvalidEntityIdException() {}
    public InvalidEntityIdException(String message) {
        super(message);
    }
}
