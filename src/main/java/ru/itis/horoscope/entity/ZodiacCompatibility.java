package ru.itis.horoscope.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;
import java.util.Objects;

@Entity
@Table(name = "zodiac_compatibility")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ZodiacCompatibility {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "zodiac_compatibility_id")
    private Integer zodiacCompatibilityId;

//    @Column(name = "sign1", nullable = false)
//    private Integer signIdMan;
//
//    @Column(name = "sign2", nullable = false)
//    private Integer signIdWoman;

    @Column(name = "percent_info", columnDefinition = "TEXT")
    private String percentInfo;

    @Column(name = "love_info", columnDefinition = "TEXT")
    private String loveInfo;

    @Column(name = "family_info", columnDefinition = "TEXT")
    private String familyInfo;

    @Column(name = "friends_info", columnDefinition = "TEXT")
    private String friendsInfo;

    @Column(name = "work_info", columnDefinition = "TEXT")
    private String workInfo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sign1", nullable = false)
    private Zodiac signMan;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sign2", nullable = false)
    private Zodiac signWoman;

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
        ZodiacCompatibility that = (ZodiacCompatibility) o;
        return getZodiacCompatibilityId() != null && Objects.equals(getZodiacCompatibilityId(), that.getZodiacCompatibilityId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy proxy
                ? proxy.getHibernateLazyInitializer().getPersistentClass().hashCode()
                : getClass().hashCode();
    }
}
