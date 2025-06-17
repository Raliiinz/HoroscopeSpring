package ru.itis.horoscope.exception;

public class UnauthorizedException extends BadRequestException {
    public UnauthorizedException(String message) {
        super(message);
    }
}
