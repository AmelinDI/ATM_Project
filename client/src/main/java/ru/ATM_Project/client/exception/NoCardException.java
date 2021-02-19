package ru.ATM_Project.client.exception;

public class NoCardException extends RuntimeException {

    public NoCardException(String message) {
        super(message);
    }
}
