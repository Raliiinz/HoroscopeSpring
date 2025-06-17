package ru.itis.horoscope.exception;

public class TooManyResetAttemptsException extends Exception {
    public TooManyResetAttemptsException(String message) {
        super(message);
    }
}
