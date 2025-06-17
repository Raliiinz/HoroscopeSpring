package ru.itis.horoscope.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.itis.horoscope.entity.AdviceForYear;
import ru.itis.horoscope.entity.Zodiac;
import ru.itis.horoscope.repository.AdviceForYearRepository;
import ru.itis.horoscope.repository.ZodiacRepository;
import ru.itis.horoscope.service.AdviceForYearService;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AdviceForYearServiceImpl implements AdviceForYearService {

    private final AdviceForYearRepository adviceForYearRepository;
    private final ZodiacRepository zodiacRepository;

    @Override
    public Optional<AdviceForYear> getAdvice(Integer zodiacId) {
        Zodiac zodiac = zodiacRepository.findById(zodiacId)
                .orElseThrow(() -> new RuntimeException("Zodiac not found"));

        return adviceForYearRepository.findByZodiac(zodiac);
    }
}
