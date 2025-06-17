package ru.itis.horoscope.exception;

public class ClientAlreadyExistsException extends BadRequestException {
    public ClientAlreadyExistsException(String phone) {
        super("Пользователь с телефоном " + phone + " уже существует");
    }
}
