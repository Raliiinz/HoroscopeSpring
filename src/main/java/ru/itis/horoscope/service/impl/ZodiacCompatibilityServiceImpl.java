package ru.itis.horoscope.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.itis.horoscope.entity.Zodiac;
import ru.itis.horoscope.entity.ZodiacCompatibility;
import ru.itis.horoscope.repository.ZodiacCompatibilityRepository;
import ru.itis.horoscope.repository.ZodiacRepository;
import ru.itis.horoscope.service.ZodiacCompatibilityService;


@Service
@RequiredArgsConstructor
public class ZodiacCompatibilityServiceImpl implements ZodiacCompatibilityService {

    private final ZodiacCompatibilityRepository zodiacCompatibilityRepository;
    private final ZodiacRepository zodiacRepository;

    @Override
    public ZodiacCompatibility getCompatibility(Integer signIdMan, Integer signIdWoman) {
        Zodiac zodiacMan = zodiacRepository.findById(signIdMan)
                .orElseThrow(() -> new RuntimeException("Знак мужчины не найден"));

        Zodiac zodiacWoman = zodiacRepository.findById(signIdWoman)
                .orElseThrow(() -> new RuntimeException("Знак женщины не найден"));

        return zodiacCompatibilityRepository.findBySigns(zodiacMan, zodiacWoman)
                .orElse(null);
    }
}
