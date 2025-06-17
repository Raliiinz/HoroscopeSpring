package ru.itis.horoscope.exception;

public class PasswordMismatchException extends BadRequestException {
    public PasswordMismatchException(String message) {
        super(message);
    }
}
