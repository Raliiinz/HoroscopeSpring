package ru.itis.horoscope.exception;

public class ZodiacNotFoundException extends RuntimeException {
    public ZodiacNotFoundException(String message) {
        super(message);
    }
}
