package ru.itis.horoscope.exception;

public class EmailSendingException extends Exception {
    public EmailSendingException(String message) {
        super(message);
    }
}
