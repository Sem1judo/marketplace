package ua.semkov.marketplace.exceptions;

public class NotEnoughAmountOfMoney extends RuntimeException {
    public NotEnoughAmountOfMoney() {
    }

    public NotEnoughAmountOfMoney(String message) {
        super(message);
    }

    public NotEnoughAmountOfMoney(String message, Throwable cause) {
        super(message, cause);
    }
}
