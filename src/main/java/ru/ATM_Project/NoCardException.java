package ru.ATM_Project;

public class NoCardException extends Exception{

    public NoCardException(String message) {
        super(message);
    }
}
