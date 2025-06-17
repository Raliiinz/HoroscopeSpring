package ru.itis.horoscope.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.itis.horoscope.entity.MoonPhaseAdvice;

import java.util.Optional;

@Repository
public interface MoonPhaseAdviceRepository extends JpaRepository<MoonPhaseAdvice, Integer> {
    Optional<MoonPhaseAdvice> findByMoonPhase(String moonPhase);
}