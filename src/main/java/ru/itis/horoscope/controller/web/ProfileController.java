package ru.itis.horoscope.controller.web;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.itis.horoscope.controller.dto.ClientDto;
import ru.itis.horoscope.controller.dto.ClientInputDto;
import ru.itis.horoscope.controller.dto.ClientOutputDto;
import ru.itis.horoscope.controller.mapper.ClientMapper;
import ru.itis.horoscope.controller.request.ChangePasswordRequest;
import ru.itis.horoscope.entity.Client;
import ru.itis.horoscope.entity.Zodiac;
import ru.itis.horoscope.exception.ClientAlreadyExistsException;
import ru.itis.horoscope.exception.PasswordMismatchException;
import ru.itis.horoscope.service.ClientService;
import ru.itis.horoscope.service.ZodiacService;
import ru.itis.horoscope.validation.InputValidator;


//@Controller
//@RequestMapping("/profile")
//public class ProfileController {
//
//    private final ClientService clientService;
//    private final ZodiacService zodiacService;
//    private final InputValidator validator;
//
//    public ProfileController(ClientService clientService,
//                             ZodiacService zodiacService,
//                             InputValidator validator) {
//        this.clientService = clientService;
//        this.zodiacService = zodiacService;
//        this.validator = validator;
//    }
//
//    @GetMapping
//    public String showProfile(Model model, HttpSession session) {
//        Client client = (Client) session.getAttribute("client");
//
//        model.addAttribute("client", client);
//
//        Zodiac zodiac = (Zodiac) session.getAttribute("zodiac");
//        if (zodiac != null) {
//            model.addAttribute("zodiac", zodiac);
//        } else {
//            model.addAttribute("error", "Знак зодиака не определен");
//        }
//
//        return "profile";
//    }
//
//    @PostMapping("/delete-account")
//    public String deleteAccount(HttpSession session,
//                                HttpServletRequest request,
//                                HttpServletResponse response,
//                                RedirectAttributes redirectAttributes) {
//
//        Client client = (Client) session.getAttribute("client");
//
//        if (client != null) {
//            try {
//                clientService.delete(client.getId());
//
//                Cookie jwtCookie = new Cookie("JWT", null);
//                jwtCookie.setMaxAge(0);
//                jwtCookie.setPath("/");
//                response.addCookie(jwtCookie);
//
//                SecurityContextHolder.clearContext();
//                request.getSession().invalidate();
//
//                return "redirect:/login";
//
//            } catch (Exception e) {
//                redirectAttributes.addFlashAttribute("error", "Ошибка при удалении аккаунта.");
//                return "redirect:/profile";
//            }
//        }
//
//        redirectAttributes.addFlashAttribute("error", "Ошибка: пользователь не авторизован.");
//        return "redirect:/profile";
//    }
//
//    @GetMapping("/edit")
//    public String showEditProfile(Model model, HttpSession session) {
//        Client client = (Client) session.getAttribute("client");
//
//        model.addAttribute("client", client);
//
//        Zodiac zodiac = (Zodiac) session.getAttribute("zodiac");
//        if (zodiac != null) {
//            model.addAttribute("zodiac", zodiac);
//        } else {
//            model.addAttribute("error", "Знак зодиака не определен");
//        }
//
//        return "profileEdit";
//    }
//
//    @PostMapping("/edit")
//    public String updateProfile(@ModelAttribute ClientOutputDto clientOutputDto,
//                                @RequestParam(value = "changePassword", required = false) String changePassword,
//                                @ModelAttribute("changePasswordRequest") ChangePasswordRequest changePasswordRequest,
//                                RedirectAttributes redirectAttributes,
//                                HttpSession session) {
//
//        Client client = (Client) session.getAttribute("client");
//        Integer clientId = client.getId();
//
//        boolean hasErrors = false;
//
//        if (validator.isNullOrEmpty(clientOutputDto.getUserName())) {
//            redirectAttributes.addFlashAttribute("errorUserName", "Имя не может быть пустым");
//            hasErrors = true;
//        }
//
//        if (validator.isNullOrEmpty(clientOutputDto.getEmail())) {
//            redirectAttributes.addFlashAttribute("errorEmail", "Email не может быть пустым");
//            hasErrors = true;
//        } else if (!validator.isValidEmail(clientOutputDto.getEmail())) {
//            redirectAttributes.addFlashAttribute("errorEmail", "Некорректный формат email");
//            hasErrors = true;
//        }
//
//        if (clientOutputDto.getBirthDate() == null) {
//            redirectAttributes.addFlashAttribute("errorBirthDate", "Дата рождения не может быть пустой");
//            hasErrors = true;
//        } else if (!validator.isValidBirthDate(clientOutputDto.getBirthDate().toString())) {
//            redirectAttributes.addFlashAttribute("errorBirthDate", "Введите корректную дату рождения");
//            hasErrors = true;
//        }
//
//        if ("on".equals(changePassword)) {
//            String newPassword = changePasswordRequest.getNewPassword();
//            String confirmNewPassword = changePasswordRequest.getConfirmNewPassword();
//
//            if (newPassword == null || newPassword.isEmpty()) {
//                redirectAttributes.addFlashAttribute("errorNewPassword", "Пароль не может быть пустым.");
//                hasErrors = true;
//            } else if (!newPassword.equals(confirmNewPassword)) {
//                redirectAttributes.addFlashAttribute("errorConfirmNewPassword", "Пароли не совпадают.");
//                hasErrors = true;
//            }
//        }
//
//        if (hasErrors) {
//            redirectAttributes.addFlashAttribute("clientDto", clientOutputDto);
//            redirectAttributes.addFlashAttribute("changePasswordRequest", changePasswordRequest);
//            return "redirect:/profile/edit";
//        }
//
//        try {
//            clientService.updateClientById(clientId, clientOutputDto);
//
//            if ("on".equals(changePassword)) {
//                clientService.changePassword(
//                        clientId,
//                        changePasswordRequest.getNewPassword(),
//                        changePasswordRequest.getConfirmNewPassword()
//                );
//            }
//
//            Client updatedClient = clientService.findClientById(clientId);
//            session.setAttribute("client", updatedClient);
//
//            if (client.getBirthDate() != null && !client.getBirthDate().equals(clientOutputDto.getBirthDate())) {
//                Zodiac zodiac = zodiacService.getZodiac(clientOutputDto.getBirthDate().toString());
//                session.setAttribute("zodiac", zodiac);
//            }
//
//            return "redirect:/profile";
//        } catch (ClientAlreadyExistsException e) {
//            redirectAttributes.addFlashAttribute("errorPhone", e.getMessage());
//        } catch (PasswordMismatchException e) {
//            redirectAttributes.addFlashAttribute("errorCurrentPassword", e.getMessage());
//        }
//
//        redirectAttributes.addFlashAttribute("clientDto", clientOutputDto);
//        redirectAttributes.addFlashAttribute("changePasswordRequest", changePasswordRequest);
//        return "redirect:/profile/edit";
//    }
//}

@Controller
@RequestMapping("/profile")
public class ProfileController {

    private final ClientService clientService;
    private final ZodiacService zodiacService;
    private final ClientMapper clientMapper;
    private final InputValidator validator;

    public ProfileController(ClientService clientService,
                             ZodiacService zodiacService,
                             ClientMapper clientMapper,
                             InputValidator validator) {
        this.clientService = clientService;
        this.zodiacService = zodiacService;
        this.clientMapper = clientMapper;
        this.validator = validator;
    }

    @GetMapping
    public String showProfile(Model model, HttpSession session) {
        Client client = (Client) session.getAttribute("client");

        model.addAttribute("client", client);

        Zodiac zodiac = (Zodiac) session.getAttribute("zodiac");
        if (zodiac != null) {
            model.addAttribute("zodiac", zodiac);
        } else {
            model.addAttribute("error", "Знак зодиака не определен");
        }

        return "profile";
    }

    @PostMapping("/delete-account")
    public String deleteAccount(HttpSession session,
                                HttpServletRequest request,
                                HttpServletResponse response,
                                RedirectAttributes redirectAttributes) {

        Client client = (Client) session.getAttribute("client");

        if (client != null) {
            try {
                clientService.delete(client.getId());

                Cookie jwtCookie = new Cookie("JWT", null);
                jwtCookie.setMaxAge(0);
                jwtCookie.setPath("/");
                response.addCookie(jwtCookie);

                SecurityContextHolder.clearContext();
                request.getSession().invalidate();

                return "redirect:/login";

            } catch (Exception e) {
                redirectAttributes.addFlashAttribute("error", "Ошибка при удалении аккаунта.");
                return "redirect:/profile";
            }
        }

        redirectAttributes.addFlashAttribute("error", "Ошибка: пользователь не авторизован.");
        return "redirect:/profile";
    }

    @GetMapping("/edit")
    public String showEditProfile(Model model, HttpSession session) {
        Client client = (Client) session.getAttribute("client");

        model.addAttribute("client", client);
        model.addAttribute("clientDto", new ClientDto(client.getUserName(), client.getEmail(), client.getBirthDate()));

        Zodiac zodiac = (Zodiac) session.getAttribute("zodiac");
        if (zodiac != null) {
            model.addAttribute("zodiac", zodiac);
        } else {
            model.addAttribute("error", "Знак зодиака не определен");
        }

        return "profileEdit";
    }

    @PostMapping("/edit")
    public String updateProfile(@ModelAttribute ClientDto clientDto,
                                @RequestParam(value = "changePassword", required = false) String changePassword,
                                @ModelAttribute("changePasswordRequest") ChangePasswordRequest changePasswordRequest,
                                RedirectAttributes redirectAttributes,
                                HttpSession session) {

        Client client = (Client) session.getAttribute("client");
        Integer clientId = client.getId();

        boolean hasErrors = false;

        if (validator.isNullOrEmpty(clientDto.getUserName())) {
            redirectAttributes.addFlashAttribute("errorUserName", "Имя не может быть пустым");
            hasErrors = true;
        }

        if (validator.isNullOrEmpty(clientDto.getEmail())) {
            redirectAttributes.addFlashAttribute("errorEmail", "Email не может быть пустым");
            hasErrors = true;
        } else if (!validator.isValidEmail(clientDto.getEmail())) {
            redirectAttributes.addFlashAttribute("errorEmail", "Некорректный формат email");
            hasErrors = true;
        }

        if (clientDto.getBirthDate() == null) {
            redirectAttributes.addFlashAttribute("errorBirthDate", "Дата рождения не может быть пустой");
            hasErrors = true;
        } else if (!validator.isValidBirthDate(clientDto.getBirthDate().toString())) {
            redirectAttributes.addFlashAttribute("errorBirthDate", "Введите корректную дату рождения");
            hasErrors = true;
        }

        if ("on".equals(changePassword)) {
            String newPassword = changePasswordRequest.getNewPassword();
            String confirmNewPassword = changePasswordRequest.getConfirmNewPassword();

            if (newPassword == null || newPassword.isEmpty()) {
                redirectAttributes.addFlashAttribute("errorNewPassword", "Пароль не может быть пустым.");
                hasErrors = true;
            } else if (!newPassword.equals(confirmNewPassword)) {
                redirectAttributes.addFlashAttribute("errorConfirmNewPassword", "Пароли не совпадают.");
                hasErrors = true;
            }
        }

        if (hasErrors) {
            redirectAttributes.addFlashAttribute("clientDto", clientDto);
            redirectAttributes.addFlashAttribute("changePasswordRequest", changePasswordRequest);
            return "redirect:/profile/edit";
        }

        try {
            clientService.updateClientById(clientId, clientMapper.toOutputDto(clientDto));

            if ("on".equals(changePassword)) {
                clientService.changePassword(
                        clientId,
                        changePasswordRequest.getNewPassword(),
                        changePasswordRequest.getConfirmNewPassword()
                );
            }

            Client updatedClient = clientService.findClientById(clientId);
            session.setAttribute("client", updatedClient);

            if (client.getBirthDate() != null && !client.getBirthDate().equals(clientDto.getBirthDate())) {
                Zodiac zodiac = zodiacService.getZodiac(clientDto.getBirthDate().toString());
                session.setAttribute("zodiac", zodiac);
            }

            return "redirect:/profile";
        } catch (ClientAlreadyExistsException e) {
            redirectAttributes.addFlashAttribute("errorPhone", e.getMessage());
        } catch (PasswordMismatchException e) {
            redirectAttributes.addFlashAttribute("errorCurrentPassword", e.getMessage());
        }

        redirectAttributes.addFlashAttribute("clientDto", clientDto);
        redirectAttributes.addFlashAttribute("changePasswordRequest", changePasswordRequest);
        return "redirect:/profile/edit";
    }
}