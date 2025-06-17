package ru.itis.horoscope.util;

import ru.itis.horoscope.library.SunCalc4JavaUtils;

import java.util.Date;
import java.util.Map;

import static ru.itis.horoscope.library.SunCalc4JavaUtils.moonCoords;

public class MoonZodiacCalculator {
    public static String getMoonZodiac(Date date) {

        double d = SunCalc4JavaUtils.toDays(date);

        /*
        Метод moonCoords из класса SunCalc4JavaUtils вычисляет геоцентрические координаты Луны (правое восхождение, деклинацию и расстояние до Луны) для определенного числа d. Результат возвращается в виде карты (Map) с тремя значениями:
        ra (right ascension) — правое восхождение Луны в радианах.
        dec (склонение) — деклинация Луны в радианах.
        dist (distance) — расстояние до Луны в километрах.
         */

        Map<String, Double> moonCoords = moonCoords(d);

        double eclipticLongitude = Math.toDegrees(moonCoords.get("ra")); // Преобразуем радианы в градусы

        // Приводим долготу в диапазон [0, 360]
        /*
        Это делается для того, чтобы долгота всегда находилась в пределах от 0 до 360 градусов,
        так как отрицательные значения или значения больше 360 не имеют смысла в данном контексте.
         */
        eclipticLongitude = (eclipticLongitude + 360) % 360;

        // Определяем знак зодиака
        if (eclipticLongitude >= 0 && eclipticLongitude < 30) return "Овен";
        if (eclipticLongitude >= 30 && eclipticLongitude < 60) return "Телец";
        if (eclipticLongitude >= 60 && eclipticLongitude < 90) return "Близнецы";
        if (eclipticLongitude >= 90 && eclipticLongitude < 120) return "Рак";
        if (eclipticLongitude >= 120 && eclipticLongitude < 150) return "Лев";
        if (eclipticLongitude >= 150 && eclipticLongitude < 180) return "Дева";
        if (eclipticLongitude >= 180 && eclipticLongitude < 210) return "Весы";
        if (eclipticLongitude >= 210 && eclipticLongitude < 240) return "Скорпион";
        if (eclipticLongitude >= 240 && eclipticLongitude < 270) return "Стрелец";
        if (eclipticLongitude >= 270 && eclipticLongitude < 300) return "Козерог";
        if (eclipticLongitude >= 300 && eclipticLongitude < 330) return "Водолей";
        if (eclipticLongitude >= 330 && eclipticLongitude < 360) return "Рыбы";

        return "Неизвестный знак";
    }
}

