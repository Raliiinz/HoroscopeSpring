package ru.itis.horoscope.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;
import java.util.Objects;

@Entity
@Table(name = "advice_for_year")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AdviceForYear {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "advice_for_year_id")
    private Integer adviceForYearId;

    @Column(name = "advice_year", nullable = false)
    private Integer adviceYear;

    @Column(name = "advice_text", nullable = false, columnDefinition = "TEXT")
    private String adviceText;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "zodiac_sign_id", nullable = false)
    private Zodiac zodiac;

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
        AdviceForYear that = (AdviceForYear) o;
        return getAdviceForYearId() != null && Objects.equals(getAdviceForYearId(), that.getAdviceForYearId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy proxy
                ? proxy.getHibernateLazyInitializer().getPersistentClass().hashCode()
                : getClass().hashCode();
    }
}
