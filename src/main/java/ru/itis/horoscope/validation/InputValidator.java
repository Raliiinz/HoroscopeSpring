package ru.itis.horoscope.validation;

import org.springframework.stereotype.Component;

import java.sql.Date;

@Component
public class InputValidator {

    public boolean isValidEmail(String email) {
        if (email == null) return false;
        String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        return email.matches(emailRegex);
    }

    public boolean isValidBirthDate(String birthDateStr) {
        try {
            Date birthDate = Date.valueOf(birthDateStr);
            Date minDate = Date.valueOf("1900-01-01");
            Date maxDate = Date.valueOf("2024-12-20");
            return !birthDate.before(minDate) && !birthDate.after(maxDate);
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    public boolean isNullOrEmpty(String value) {
        return value == null || value.isEmpty();
    }
}
