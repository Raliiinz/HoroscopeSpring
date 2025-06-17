package ru.itis.horoscope.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;
import java.util.Objects;

@Entity
@Table(name = "moon_phase_advice")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MoonPhaseAdvice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "moon_phase", nullable = false)
    private String moonPhase;

    @Column(name = "general_info", columnDefinition = "TEXT")
    private String generalInfo;

    @Column(columnDefinition = "TEXT")
    private String affairs;

    @Column(columnDefinition = "TEXT")
    private String work;

    @Column(name = "home_work", columnDefinition = "TEXT")
    private String homeWork;

    @Column(columnDefinition = "TEXT")
    private String money;

    @Column(name = "love_relationships", columnDefinition = "TEXT")
    private String loveRelationships;

    @Column(columnDefinition = "TEXT")
    private String communication;

    @Column(columnDefinition = "TEXT")
    private String travel;

    @Column(name = "hair_care", columnDefinition = "TEXT")
    private String hairCare;

    @Column(name = "beauty_self_care", columnDefinition = "TEXT")
    private String beautySelfCare;

    @Column(columnDefinition = "TEXT")
    private String health;

    @Column(columnDefinition = "TEXT")
    private String nutrition;

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
        MoonPhaseAdvice that = (MoonPhaseAdvice) o;
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy proxy
                ? proxy.getHibernateLazyInitializer().getPersistentClass().hashCode()
                : getClass().hashCode();
    }
}