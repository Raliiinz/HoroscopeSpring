package ru.itis.horoscope.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.itis.horoscope.entity.ZodiacLunarSignAdvice;

import java.util.Optional;

@Repository
public interface ZodiacLunarSignAdviceRepository extends JpaRepository<ZodiacLunarSignAdvice, Integer> {

    Optional<ZodiacLunarSignAdvice> findByZodiacSign(String zodiacSign);
}
