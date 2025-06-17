package ru.itis.horoscope.service;

import ru.itis.horoscope.entity.MoonPhaseAdvice;

public interface MoonPhaseAdviceService {
    MoonPhaseAdvice getMoonPhaseAdvice(String moonPhase);
}
