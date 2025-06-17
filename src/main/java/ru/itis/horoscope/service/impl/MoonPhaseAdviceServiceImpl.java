package ru.itis.horoscope.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.itis.horoscope.entity.MoonPhaseAdvice;
import ru.itis.horoscope.repository.MoonPhaseAdviceRepository;
import ru.itis.horoscope.service.MoonPhaseAdviceService;

@Service
@RequiredArgsConstructor
public class MoonPhaseAdviceServiceImpl implements MoonPhaseAdviceService {

    private final MoonPhaseAdviceRepository moonPhaseAdviceRepository;

    @Override
    public MoonPhaseAdvice getMoonPhaseAdvice(String moonPhase) {
        return moonPhaseAdviceRepository.findByMoonPhase(moonPhase)
                .orElse(null);
    }
}
