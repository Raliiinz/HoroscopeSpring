package ru.itis.horoscope.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;
import java.util.Objects;

@Entity
@Table(name = "zodiac_lunar_sign_advice")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ZodiacLunarSignAdvice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "zodiac_sign", nullable = false)
    private String zodiacSign;

    @Column(name = "general_info", columnDefinition = "TEXT")
    private String generalInfo;

    @Column(columnDefinition = "TEXT")
    private String possibilities;

    @Column(columnDefinition = "TEXT")
    private String prohibitions;

    @Column(columnDefinition = "TEXT")
    private String mood;

    @Column(columnDefinition = "TEXT")
    private String initiatives;

    @Column(name = "work_business", columnDefinition = "TEXT")
    private String workBusiness;

    @Column(columnDefinition = "TEXT")
    private String finances;

    @Column(name = "love_family", columnDefinition = "TEXT")
    private String loveFamily;

    @Column(columnDefinition = "TEXT")
    private String health;

    @Column(name = "creativity_education", columnDefinition = "TEXT")
    private String creativityEducation;

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy proxy
                ? proxy.getHibernateLazyInitializer().getPersistentClass()
                : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy proxy
                ? proxy.getHibernateLazyInitializer().getPersistentClass()
                : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        ZodiacLunarSignAdvice that = (ZodiacLunarSignAdvice) o;
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy proxy
                ? proxy.getHibernateLazyInitializer().getPersistentClass().hashCode()
                : getClass().hashCode();
    }
}
