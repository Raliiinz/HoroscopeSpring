package ru.itis.horoscope.service;

import ru.itis.horoscope.entity.Zodiac;

public interface ZodiacService {
    Zodiac getZodiac(String birthDate);
    Integer getZodiacSign(String birthDate);
    Zodiac getZodiacWithStones(Integer signId);
}
