package ru.itis.horoscope.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.itis.horoscope.entity.Zodiac;
import ru.itis.horoscope.entity.ZodiacCompatibility;
import java.util.Optional;


@Repository
public interface ZodiacCompatibilityRepository extends JpaRepository<ZodiacCompatibility, Integer> {

    @Query("SELECT z FROM ZodiacCompatibility z WHERE z.signMan = :signMan AND z.signWoman = :signWoman")
    Optional<ZodiacCompatibility> findBySigns(
            @Param("signMan") Zodiac signMan,
            @Param("signWoman") Zodiac signWoman);
}
