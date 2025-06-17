package ru.itis.horoscope.service;

import ru.itis.horoscope.entity.ZodiacCompatibility;

public interface ZodiacCompatibilityService {
    ZodiacCompatibility getCompatibility(Integer signIdMan, Integer signIdWoman);
}
