package ru.itis.horoscope.util;

import ru.itis.horoscope.library.SunCalc4JavaUtils;

import java.util.Date;
import java.util.Map;

public class MoonPhaseCalculator {
    public static String getMoonPhase(Date date) {

        /*
        Возвращается Map с ключами:
        "fraction" — доля освещенной поверхности Луны (значение от 0 до 1).
        "phase" — текущая фаза Луны (значение от 0 до 1).
        "angle" — угол освещения Луны.
         */
        Map<String, Double> moonIllumination = SunCalc4JavaUtils.getMoonIllumination(date);

        // Получаем значение фазы
        double phase = moonIllumination.get("phase");
        // Сопоставляем фазу с её названием
        if (phase >= 0 && phase < 0.03) return "Новолуние"; // 0 ± 0.03
        if (phase >= 0.03 && phase < 0.25) return "Растущий серп";
        if (phase >= 0.25 && phase < 0.28) return "Первая четверть"; // 0.25 ± 0.03
        if (phase >= 0.28 && phase < 0.5) return "Растущая луна";
        if (phase >= 0.5 && phase < 0.53) return "Полнолуние"; // 0.5 ± 0.03
        if (phase >= 0.53 && phase < 0.75) return "Убывающая луна";
        if (phase >= 0.75 && phase < 0.78) return "Последняя четверть"; // 0.75 ± 0.03
        if (phase >= 0.78 && phase <= 1.0) return "Убывающий серп";
        return "Неизвестная фаза";
    }

}
