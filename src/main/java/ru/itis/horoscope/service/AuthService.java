package ru.itis.horoscope.service;

public interface  AuthService <T>{
    T login(String email, String password);
    T register(String email, String username, String password, String confirmPassword, java.sql.Date birthDate);
}

