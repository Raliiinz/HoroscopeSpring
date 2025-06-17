package ru.itis.horoscope.controller.web;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.itis.horoscope.entity.Client;
import ru.itis.horoscope.entity.MoonPhaseAdvice;
import ru.itis.horoscope.entity.Zodiac;
import ru.itis.horoscope.entity.ZodiacLunarSignAdvice;
import ru.itis.horoscope.service.MoonPhaseAdviceService;
import ru.itis.horoscope.service.ZodiacLunarSignAdviceService;
import ru.itis.horoscope.service.ZodiacService;
import ru.itis.horoscope.util.MoonPhaseCalculator;
import ru.itis.horoscope.util.MoonZodiacCalculator;
import java.sql.Date;

@Controller
public class MainController {

    private final ZodiacService zodiacService;
    private final MoonPhaseAdviceService moonPhaseAdviceService;
    private final ZodiacLunarSignAdviceService zodiacLunarSignAdviceService;

    public MainController(ZodiacService zodiacService,
                          MoonPhaseAdviceService moonPhaseAdviceService,
                          ZodiacLunarSignAdviceService zodiacLunarSignAdviceService) {
        this.zodiacService = zodiacService;
        this.moonPhaseAdviceService = moonPhaseAdviceService;
        this.zodiacLunarSignAdviceService = zodiacLunarSignAdviceService;
    }

    @GetMapping("/")
    public String showIndexPage() {
        return "index";
    }


    @GetMapping("/main")
    public String showMainPage(Model model, HttpSession session) {

        Client client = (Client) session.getAttribute("client");

        Date now = new Date(System.currentTimeMillis());
        String moonPhase = MoonPhaseCalculator.getMoonPhase(now);
        String moonZodiacSign = MoonZodiacCalculator.getMoonZodiac(now);

        try {
            MoonPhaseAdvice moonPhaseAdvice = moonPhaseAdviceService.getMoonPhaseAdvice(moonPhase);
            model.addAttribute("moonPhaseAdvice", moonPhaseAdvice);
        } catch (Exception e) {
            model.addAttribute("error", "Не удалось получить информацию по фазе луны");
        }

        try {
            ZodiacLunarSignAdvice zodiacSignAdvice = zodiacLunarSignAdviceService.getZodiacLunarSignAdvice(moonZodiacSign);
            model.addAttribute("zodiacSignAdvice", zodiacSignAdvice);
        } catch (Exception e) {
            model.addAttribute("error", "Не удалось получить информацию по знаку зодиака луны");
        }

        Date birthDate = client.getBirthDate();
        if (birthDate != null) {
            try {
                Zodiac zodiac = zodiacService.getZodiac(birthDate.toString());
                model.addAttribute("zodiac", zodiac);
                session.setAttribute("zodiac", zodiac);
            } catch (Exception e) {
                model.addAttribute("error", "Не удалось определить знак зодиака");
                session.removeAttribute("zodiac");
            }
        } else {
            model.addAttribute("error", "Дата рождения не указана");
            session.removeAttribute("zodiac");
        }

        return "main";
    }
}
