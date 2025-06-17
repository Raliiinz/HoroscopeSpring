package ru.itis.horoscope.service;

import ru.itis.horoscope.entity.ZodiacLunarSignAdvice;

public interface ZodiacLunarSignAdviceService {
    ZodiacLunarSignAdvice getZodiacLunarSignAdvice(String zodiacSign);
}