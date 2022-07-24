package ua.semkov.marketplace.exceptions;

public class ProductAlreadyExistException extends RuntimeException {
    public ProductAlreadyExistException() {
    }

    public ProductAlreadyExistException(String message) {
        super(message);
    }

    public ProductAlreadyExistException(String message, Throwable cause) {
        super(message, cause);
    }

}