package ru.itis.horoscope.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.itis.horoscope.entity.AdviceForYear;
import ru.itis.horoscope.entity.Zodiac;

import java.util.Optional;

@Repository
public interface AdviceForYearRepository extends JpaRepository<AdviceForYear, Integer> {
    Optional<AdviceForYear> findByZodiac(Zodiac zodiac);
}