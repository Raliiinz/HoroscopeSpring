package ru.itis.horoscope.controller.web;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.itis.horoscope.entity.AdviceForYear;
import ru.itis.horoscope.entity.Zodiac;
import ru.itis.horoscope.service.AdviceForYearService;

import java.util.Optional;

@Controller
public class AdviceForYearController {

    private final AdviceForYearService adviceForYearService;

    public AdviceForYearController(AdviceForYearService adviceForYearService) {
        this.adviceForYearService = adviceForYearService;
    }

    @GetMapping("/adviceForYear")
    public String getAdviceForYear(Model model, HttpSession session) {
        Zodiac zodiac = (Zodiac) session.getAttribute("zodiac");

        if (zodiac == null) {
            model.addAttribute("error", "Знак зодиака не определен");
            return "adviceForYear";
        }

        try {
            Optional<AdviceForYear> optionalAdvice = adviceForYearService.getAdvice(zodiac.getSignId());

            if (optionalAdvice.isPresent()) {
                model.addAttribute("advice", optionalAdvice.get());
            } else {
                model.addAttribute("error", "Совет на год не найден");
            }

        } catch (Exception e) {
            model.addAttribute("error", "Произошла ошибка при получении совета");
        }
        return "adviceForYear";
    }
}
