package ru.itis.horoscope.controller.web;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.itis.horoscope.controller.dto.ZodiacDto;
import ru.itis.horoscope.controller.mapper.ZodiacMapper;
import ru.itis.horoscope.entity.Zodiac;
import ru.itis.horoscope.service.ZodiacService;

@Controller
@RequiredArgsConstructor
public class SignInfoController {

    private final ZodiacMapper zodiacMapper;
    private final ZodiacService zodiacService;

    @GetMapping("/signInfo")
    public String showSignInfo(Model model, HttpSession session) {
        Zodiac zodiac = (Zodiac) session.getAttribute("zodiac");
        if (zodiac != null) {
            // Загружаем зодиак с камнями из базы
            Zodiac zodiacWithStones = zodiacService.getZodiacWithStones(zodiac.getSignId());
            ZodiacDto zodiacDto = zodiacMapper.toDto(zodiacWithStones);
            model.addAttribute("zodiac", zodiacDto);
//            ZodiacDto zodiacDto = zodiacMapper.toDto(zodiac);
//            model.addAttribute("zodiac", zodiacDto);

            // Добавляем URL внешнего сервиса
            model.addAttribute("stoneServiceUrl", "http://localhost:5001");
        } else {
            model.addAttribute("error", "Знак зодиака не определен");
        }

        return "signInfo";
    }
}
