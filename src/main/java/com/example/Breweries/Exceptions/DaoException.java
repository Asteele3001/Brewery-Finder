package com.example.Breweries.Exceptions;

public class DaoException extends RuntimeException {
    public DaoException() {
        super();
    }
    public DaoException(String message) {
        super(message);
    }
    public DaoException(String message, Exception cause) {
        super(message, cause);
    }
}
