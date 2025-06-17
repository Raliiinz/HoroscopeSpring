package ru.itis.horoscope.exception;

public class InvalidCredentialsException extends BadRequestException {
    public InvalidCredentialsException() {
        super("Неверные учетные данные");
    }
}
