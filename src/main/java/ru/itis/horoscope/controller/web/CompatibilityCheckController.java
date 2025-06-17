package ru.itis.horoscope.controller.web;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.itis.horoscope.entity.ZodiacCompatibility;
import ru.itis.horoscope.service.ZodiacCompatibilityService;
import ru.itis.horoscope.service.ZodiacService;
import ru.itis.horoscope.validation.InputValidator;

import java.sql.Date;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/compatibilityCheck")
public class CompatibilityCheckController {

    private final ZodiacService zodiacService;
    private final ZodiacCompatibilityService zodiacCompatibilityService;
    private final InputValidator validator;

    public CompatibilityCheckController(ZodiacService zodiacService,
                                        ZodiacCompatibilityService zodiacCompatibilityService,
                                        InputValidator validator) {
        this.zodiacService = zodiacService;
        this.zodiacCompatibilityService = zodiacCompatibilityService;
        this.validator = validator;
    }

    @GetMapping
    public String showCheckForm() {
        return "compatibilityCheck";
    }

    @PostMapping
    @ResponseBody
    public ResponseEntity<Map<String, Object>> checkCompatibility(
            @RequestBody Map<String, String> dates) {

        String manBirthDate = dates.get("manBirthDate");
        String womanBirthDate = dates.get("womanBirthDate");

        Map<String, Object> response = new HashMap<>();

        if (validator.isNullOrEmpty(manBirthDate)) {
            response.put("error", "Пожалуйста, укажите дату рождения мужчины");
            return ResponseEntity.badRequest().body(response);
        }

        if (validator.isNullOrEmpty(womanBirthDate)) {
            response.put("error", "Пожалуйста, укажите дату рождения женщины");
            return ResponseEntity.badRequest().body(response);
        }

        if (!validator.isValidBirthDate(manBirthDate) || !validator.isValidBirthDate(womanBirthDate)) {
            response.put("error", "Введите корректные даты рождения");
            return ResponseEntity.badRequest().body(response);
        }

        try {
            Integer manSign = zodiacService.getZodiacSign(manBirthDate);
            Integer womanSign = zodiacService.getZodiacSign(womanBirthDate);

            ZodiacCompatibility result = zodiacCompatibilityService.getCompatibility(manSign, womanSign);

            if (result != null) {
                response.put("percentInfo", result.getPercentInfo());
                response.put("loveInfo", result.getLoveInfo());
                response.put("familyInfo", result.getFamilyInfo());
                response.put("friendsInfo", result.getFriendsInfo());
                response.put("workInfo", result.getWorkInfo());
                return ResponseEntity.ok(response);
            } else {
                response.put("error", "Совместимость не найдена");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
        } catch (Exception e) {
            response.put("error", "Произошла ошибка при проверке совместимости");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

//    @PostMapping
//    public String checkCompatibility(
//            @RequestParam String manBirthDate,
//            @RequestParam String womanBirthDate,
//            Model model) {
//
//        if (validator.isNullOrEmpty(manBirthDate) || validator.isNullOrEmpty(womanBirthDate)) {
//            model.addAttribute("error", "Пожалуйста, заполните оба поля даты рождения");
//            return "compatibilityCheck";
//        }
//
//        if (!validator.isValidBirthDate(manBirthDate) || !validator.isValidBirthDate(womanBirthDate)) {
//            model.addAttribute("error", "Введите корректные даты рождения");
//            return "compatibilityCheck";
//        }
//
//        try {
//            Integer manSign = zodiacService.getZodiacSign(manBirthDate);
//            Integer womanSign = zodiacService.getZodiacSign(womanBirthDate);
//
//            ZodiacCompatibility result = zodiacCompatibilityService.getCompatibility(manSign, womanSign);
//
//            if (result != null) {
//                model.addAttribute("result", result);
//                return "compatibilityResult";
//            } else {
//                model.addAttribute("error", "Совместимость не найдена");
//                return "compatibilityCheck";
//            }
//        } catch (Exception e) {
//            model.addAttribute("error", "Произошла ошибка при проверке совместимости");
//            return "compatibilityCheck";
//        }
//    }

}