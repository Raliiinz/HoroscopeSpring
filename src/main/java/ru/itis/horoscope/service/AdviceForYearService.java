package ru.itis.horoscope.service;


import ru.itis.horoscope.entity.AdviceForYear;

import java.util.Optional;

public interface AdviceForYearService {
    Optional<AdviceForYear> getAdvice(Integer signId);
}
