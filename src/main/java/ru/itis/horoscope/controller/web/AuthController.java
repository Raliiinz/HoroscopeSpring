package ru.itis.horoscope.controller.web;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.itis.horoscope.controller.request.LoginClientRequest;
import ru.itis.horoscope.controller.request.RegisterClientRequest;
import ru.itis.horoscope.entity.Client;
import ru.itis.horoscope.exception.ClientAlreadyExistsException;
import ru.itis.horoscope.exception.InvalidCredentialsException;
import ru.itis.horoscope.security.JwtTokenUtils;
import ru.itis.horoscope.service.ClientAuthService;
import java.sql.Date;
import java.time.LocalDate;

@Controller
@RequiredArgsConstructor
public class AuthController {

    private final ClientAuthService clientAuthService;
    private final UserDetailsService userDetailsService;
    private final JwtTokenUtils jwtTokenUtils;

    @GetMapping("/login")
    public String showLoginPage(Model model) {
        if (!model.containsAttribute("loginClientRequest")) {
            model.addAttribute("loginClientRequest", new LoginClientRequest());
        }
        return "login";

//        if (!model.containsAttribute("error")) {
//            model.addAttribute("error", "");
//        }
//        return "login";
    }


    @PostMapping("/login")
    public String login(
            @Valid @ModelAttribute("loginClientRequest") LoginClientRequest loginClientRequest,
            BindingResult result,
            HttpServletResponse response,
            RedirectAttributes redirectAttributes
    ) {
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.loginClientRequest", result);
            redirectAttributes.addFlashAttribute("loginClientRequest", loginClientRequest);
            return "redirect:/login";
        }

        try {
            Client client = clientAuthService.login(loginClientRequest.getEmail(), loginClientRequest.getPassword());
            UserDetails userDetails = userDetailsService.loadUserByUsername(loginClientRequest.getEmail());
            String token = jwtTokenUtils.generateToken(client.getId(), userDetails);

            Cookie jwtCookie = new Cookie("JWT", token);
            jwtCookie.setHttpOnly(true);
            jwtCookie.setPath("/");
            jwtCookie.setMaxAge(30 * 60); // 30 минут
            response.addCookie(jwtCookie);

            return "redirect:/main";

        } catch (InvalidCredentialsException e) {
            redirectAttributes.addFlashAttribute("error", "Неверный email или пароль");
            redirectAttributes.addFlashAttribute("loginClientRequest", loginClientRequest);
            return "redirect:/login";
        }
    }

//    @PostMapping("/login")
//    public String login(
//            @ModelAttribute("loginClientRequest") LoginClientRequest loginClientRequest,
//            HttpServletRequest request,
//            HttpServletResponse response,
//            RedirectAttributes redirectAttributes
//    ) {
//        request.getSession().invalidate();
//
//        String email = loginClientRequest.getEmail();
//        String password = loginClientRequest.getPassword();
//
//        boolean hasErrors = false;
//
//        if (email == null || email.isEmpty()) {
//            redirectAttributes.addFlashAttribute("errorEmail", "Почта не может быть пустой");
//            hasErrors = true;
//        }
//
//        if (password == null || password.isEmpty()) {
//            redirectAttributes.addFlashAttribute("errorPassword", "Пароль не может быть пустым");
//            hasErrors = true;
//        }
//
//        if (hasErrors) {
//            redirectAttributes.addFlashAttribute("email", email);
//            return "redirect:/login";
//        }
//
//        try {
//            Client client = clientAuthService.login(email, password);
//            UserDetails userDetails = userDetailsService.loadUserByUsername(email);
//            String token = jwtTokenUtils.generateToken(client.getId(), userDetails);
//            Cookie jwtCookie = new Cookie("JWT", token);
//            jwtCookie.setHttpOnly(true);
//            jwtCookie.setPath("/");
//            jwtCookie.setMaxAge(30 * 60);
//
//            response.addCookie(jwtCookie);
//
//            return "redirect:/main";
//
//        } catch (InvalidCredentialsException e) {
//            redirectAttributes.addFlashAttribute("error", "Неверный телефон или пароль");
//            redirectAttributes.addFlashAttribute("email", email);
//            return "redirect:/login";
//        }
//    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        Cookie jwtCookie = new Cookie("JWT", null);
        jwtCookie.setMaxAge(0);
        jwtCookie.setPath("/");
        response.addCookie(jwtCookie);

        SecurityContextHolder.clearContext();
        request.getSession().invalidate();

        return "redirect:/login?logout";
    }

    @GetMapping("/register")
    public String showRegistrationPage(Model model) {
        if (!model.containsAttribute("registerClientRequest")) {
            model.addAttribute("registerClientRequest", new RegisterClientRequest());
        }
        return "registration";


//        if (!model.containsAttribute("errorEmail")) {
//            model.addAttribute("errorEmail", "");
//        }
//        if (!model.containsAttribute("errorPassword")) {
//            model.addAttribute("errorPassword", "");
//        }
//        if (!model.containsAttribute("errorConfirmPassword")) {
//            model.addAttribute("errorConfirmPassword", "");
//        }
//        if (!model.containsAttribute("errorUserName")) {
//            model.addAttribute("errorUserName", "");
//        }
//        if (!model.containsAttribute("errorBirthDate")) {
//            model.addAttribute("errorBirthDate", "");
//        }
//        return "registration";
    }

    @PostMapping("/register")
    public String register(
            @Valid @ModelAttribute("registerClientRequest") RegisterClientRequest registerClientRequest,
            BindingResult result,
            RedirectAttributes redirectAttributes
    ) {
        // Если есть ошибки валидации — вернуть обратно
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.registerClientRequest", result);
            redirectAttributes.addFlashAttribute("registerClientRequest", registerClientRequest);
            return "redirect:/register";
        }

        String password = registerClientRequest.getPassword();
        String confirmPassword = registerClientRequest.getConfirmPassword();

        // Сравнение паролей — можно перенести в собственный Validator или сам DTO
        if (!password.equals(confirmPassword)) {
            redirectAttributes.addFlashAttribute("errorConfirmPassword", "Пароли не совпадают.");
            redirectAttributes.addFlashAttribute("registerClientRequest", registerClientRequest);
            return "redirect:/register";
        }

        try {
            clientAuthService.register(
                    registerClientRequest.getEmail(),
                    registerClientRequest.getUserName(),
                    password,
                    confirmPassword,
                    Date.valueOf(registerClientRequest.getBirthDate())
            );

            redirectAttributes.addFlashAttribute("registrationSuccess", "Регистрация прошла успешно!");
            return "redirect:/login";

        } catch (ClientAlreadyExistsException e) {
            redirectAttributes.addFlashAttribute("errorEmail", e.getMessage());
            redirectAttributes.addFlashAttribute("registerClientRequest", registerClientRequest);
            return "redirect:/register";
        }
    }

//    @PostMapping("/register")
//    public String register(
//            @Valid @ModelAttribute("registerClientRequest") RegisterClientRequest registerClientRequest,
//            RedirectAttributes redirectAttributes
//    ) {
//        String userName = registerClientRequest.getUserName();
//        String birthDateStr = registerClientRequest.getBirthDate();
//        String email = registerClientRequest.getEmail();
//        String password = registerClientRequest.getPassword();
//        String confirmPassword = registerClientRequest.getConfirmPassword();
//
//        boolean hasErrors = false;
//
//        if (userName == null || userName.isEmpty()) {
//            redirectAttributes.addFlashAttribute("errorUserName", "Имя не может быть пустым.");
//            hasErrors = true;
//        }
//
//        if (email == null || email.isEmpty()) {
//            redirectAttributes.addFlashAttribute("errorEmail", "Почта не может быть пустой.");
//            hasErrors = true;
//        }
//        if (password == null || password.isEmpty()) {
//            redirectAttributes.addFlashAttribute("errorPassword", "Пароль не может быть пустым.");
//            hasErrors = true;
//        }
//
//        if (!password.equals(confirmPassword)) {
//            redirectAttributes.addFlashAttribute("errorConfirmPassword", "Пароли не совпадают.");
//            hasErrors = true;
//        }
//
//        Date birthDate = null;
//        if (birthDateStr == null || birthDateStr.trim().isEmpty()) {
//            redirectAttributes.addFlashAttribute("errorBirthDate", "Дата рождения не может быть пустой");
//            hasErrors = true;
//        } else {
//            try {
//                birthDate = Date.valueOf(birthDateStr);
//                LocalDate minDate = LocalDate.of(1900, 1, 1);
//                LocalDate maxDate = LocalDate.now();
//
//                if (birthDate.toLocalDate().isBefore(minDate)) {
//                    redirectAttributes.addFlashAttribute("errorBirthDate", "Дата рождения не может быть раньше 1900 года");
//                    hasErrors = true;
//                } else if (birthDate.toLocalDate().isAfter(maxDate)) {
//                    redirectAttributes.addFlashAttribute("errorBirthDate", "Дата рождения не может быть в будущем");
//                    hasErrors = true;
//                }
//            } catch (IllegalArgumentException e) {
//                redirectAttributes.addFlashAttribute("errorBirthDate", "Неверный формат даты. Используйте ГГГГ-ММ-ДД");
//                hasErrors = true;
//            }
//        }
//
//        if (hasErrors) {
//            redirectAttributes.addFlashAttribute("userName", userName);
//            redirectAttributes.addFlashAttribute("birthDate", birthDateStr);
//            redirectAttributes.addFlashAttribute("email", email);
//            return "redirect:/register";
//        }
//
//        try {
//            clientAuthService.register(email, userName,password, confirmPassword, birthDate);
//
//            redirectAttributes.addFlashAttribute("registrationSuccess", "Регистрация прошла успешно! Теперь вы можете войти.");
//
//            return "redirect:/login";
//        } catch (ClientAlreadyExistsException e) {
//            redirectAttributes.addFlashAttribute("errorEmail", e.getMessage());
//            redirectAttributes.addFlashAttribute("userName", userName);
//            redirectAttributes.addFlashAttribute("birthDate", birthDateStr);
//            redirectAttributes.addFlashAttribute("email", email);
//            return "redirect:/register";
//        }
//    }
}
