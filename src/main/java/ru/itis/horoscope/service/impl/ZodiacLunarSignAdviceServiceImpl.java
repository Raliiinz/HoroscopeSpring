package ru.itis.horoscope.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.itis.horoscope.entity.ZodiacLunarSignAdvice;
import ru.itis.horoscope.repository.ZodiacLunarSignAdviceRepository;
import ru.itis.horoscope.service.ZodiacLunarSignAdviceService;


@Service
@RequiredArgsConstructor
public class ZodiacLunarSignAdviceServiceImpl implements ZodiacLunarSignAdviceService {

    private final ZodiacLunarSignAdviceRepository zodiacLunarSignAdviceRepository;

    @Override
    public ZodiacLunarSignAdvice getZodiacLunarSignAdvice(String zodiacSign) {
        return zodiacLunarSignAdviceRepository.findByZodiacSign(zodiacSign)
                .orElse(null);
    }
}
