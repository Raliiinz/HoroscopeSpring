package ru.itis.horoscope.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.itis.horoscope.entity.Zodiac;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface ZodiacRepository extends JpaRepository<Zodiac, Integer> {

    @Query("SELECT z FROM Zodiac z WHERE " +
            "(z.startMonth = :month AND z.startDay <= :day) OR " +
            "(z.endMonth = :month AND z.endDay >= :day)")
    Optional<Zodiac> findByBirthDate(
            @Param("month") int month,
            @Param("day") int day);


    @Query("SELECT z.signId FROM Zodiac z WHERE " +
            "(z.startMonth = :month AND z.startDay <= :day) OR " +
            "(z.endMonth = :month AND z.endDay >= :day)")
    Optional<Integer> findSignIdByBirthDate(
            @Param("month") int month,
            @Param("day") int day);

    default Optional<Zodiac> findByBirthDate(LocalDate birthDate) {
        return findByBirthDate(birthDate.getMonthValue(), birthDate.getDayOfMonth());
    }

    default Optional<Integer> findSignIdByBirthDate(LocalDate birthDate) {
        return findSignIdByBirthDate(birthDate.getMonthValue(), birthDate.getDayOfMonth());
    }

    @Query("SELECT z FROM Zodiac z LEFT JOIN FETCH z.stones WHERE z.signId = :signId")
    Zodiac findByIdWithStones(@Param("signId") Integer signId);
}

