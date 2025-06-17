package ru.itis.horoscope.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;

import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "zodiac_signs")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Zodiac {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sign_id")
    private Integer signId;

    @Column(name = "sign_name", nullable = false)
    private String signName;

    @Column(name = "end_month", nullable = false)
    private Integer endMonth;

    @Column(name = "start_day", nullable = false)
    private Integer startDay;

    @Column(name = "end_day", nullable = false)
    private Integer endDay;

    @Column(name = "start_month", nullable = false)
    private Integer startMonth;

    @Column(name = "strong_side", columnDefinition = "TEXT")
    private String strongSide;

    @Column(name = "little_weakness", columnDefinition = "TEXT")
    private String weakness;

    @Column(name = "information", columnDefinition = "TEXT")
    private String info;

    @Column(name = "image_path")
    private String imagePath;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "zodiac_stones",
            joinColumns = @JoinColumn(name = "zodiac_sign_id"),
            inverseJoinColumns = @JoinColumn(name = "stone_id")
    )
    private List<Stone> stones;

    @OneToMany(mappedBy = "zodiac", fetch = FetchType.LAZY)
    private List<AdviceForYear> yearAdvices;

    @OneToMany(mappedBy = "signMan", fetch = FetchType.LAZY)
    private List<ZodiacCompatibility> compatibilitiesAsMan;

    @OneToMany(mappedBy = "signWoman", fetch = FetchType.LAZY)
    private List<ZodiacCompatibility> compatibilitiesAsWoman;

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
        Zodiac zodiac = (Zodiac) o;
        return getSignId() != null && Objects.equals(getSignId(), zodiac.getSignId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy proxy
                ? proxy.getHibernateLazyInitializer().getPersistentClass().hashCode()
                : getClass().hashCode();
    }
}

