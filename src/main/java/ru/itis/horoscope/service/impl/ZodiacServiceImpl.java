package ru.itis.horoscope.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.itis.horoscope.entity.Zodiac;
import ru.itis.horoscope.repository.ZodiacCompatibilityRepository;
import ru.itis.horoscope.repository.ZodiacRepository;
import ru.itis.horoscope.service.ZodiacService;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
public class ZodiacServiceImpl implements ZodiacService {

    private final ZodiacRepository zodiacRepository;

    @Override
    public Zodiac getZodiac(String birthDate) {
        LocalDate date = LocalDate.parse(birthDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        return zodiacRepository.findByBirthDate(date)
                .orElse(null);
    }

    @Override
    public Integer getZodiacSign(String birthDate) {
        LocalDate date = LocalDate.parse(birthDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        return zodiacRepository.findSignIdByBirthDate(date)
                .orElse(null);
    }

    @Override
    public Zodiac getZodiacWithStones(Integer signId) {
        return zodiacRepository.findByIdWithStones(signId);
    }

}
