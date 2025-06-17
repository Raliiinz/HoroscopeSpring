package ru.itis.horoscope.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.itis.horoscope.entity.Client;

import java.sql.Date;
import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client, Integer> {

    Optional<Client> findByEmail(String email);

//    boolean existsByEmail(String email);

//    @Modifying
//    @Query("UPDATE Client c SET c.userName = :userName, c.birthDate = :birthDate, c.password = :password WHERE c.id = :id")
//    void updateUser(
//            @Param("userName") String userName,
//            @Param("birthDate") Date birthDate,
//            @Param("password") String password,
//            @Param("id") Integer id);

//    @Modifying
//    @Query("DELETE FROM Client c WHERE c.id = :id")
//    void deleteById(@Param("id") Integer id);
}
