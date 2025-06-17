package ru.itis.horoscope.controller.web;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.itis.horoscope.controller.request.LoginClientRequest;
import ru.itis.horoscope.controller.request.RegisterClientRequest;
import ru.itis.horoscope.controller.request.ResetPasswordRequest;
import ru.itis.horoscope.entity.Client;
import ru.itis.horoscope.exception.*;
import ru.itis.horoscope.security.JwtTokenUtils;
import ru.itis.horoscope.service.BrevoService;
import ru.itis.horoscope.service.ClientAuthService;
import java.sql.Date;
import java.time.LocalDate;
import java.util.Map;

@Slf4j
@Controller
@RequiredArgsConstructor
public class AuthController {

    private final ClientAuthService clientAuthService;
    private final UserDetailsService userDetailsService;
    private final JwtTokenUtils jwtTokenUtils;
    private final BrevoService brevoService;

    @GetMapping("/login")
    public String showLoginPage(Model model) {
        if (!model.containsAttribute("loginClientRequest")) {
            model.addAttribute("loginClientRequest", new LoginClientRequest());
        }
        return "login";
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
    }

    @PostMapping("/register")
    public String register(
            @Valid @ModelAttribute("registerClientRequest") RegisterClientRequest registerClientRequest,
            BindingResult result,
            RedirectAttributes redirectAttributes
    ) {
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.registerClientRequest", result);
            redirectAttributes.addFlashAttribute("registerClientRequest", registerClientRequest);
            return "redirect:/register";
        }

        String password = registerClientRequest.getPassword();
        String confirmPassword = registerClientRequest.getConfirmPassword();

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

//    @PostMapping("/api/auth/forgot-password")
//    @ResponseBody
//    public ResponseEntity<?> forgotPassword(@RequestParam String email) {
//        try {
//            // Логируем полученный email
//            System.out.println("Получен запрос для email: " + email);
//
//            String resetToken = clientAuthService.generatePasswordResetToken(email);
//            System.out.println("Сгенерирован токен: " + resetToken);
//
//            // Временный ответ без реальной отправки письма
//            return ResponseEntity.ok().body(Map.of(
//                    "success", true,
//                    "message", "Тестовый режим: запрос получен (email: " + email + ")"
//            ));
//
//        } catch (Exception e) {
//            return ResponseEntity.status(500).body(Map.of(
//                    "success", false,
//                    "message", "Тестовая ошибка: " + e.getMessage()
//            ));
//        }
//    }

//    @GetMapping("/test-email")
//    @ResponseBody
//    public ResponseEntity<?> testEmail(@RequestParam String email) throws EmailSendingException {
//        boolean result = brevoService.sendPasswordResetEmail(email, "test-token-123");
//        return ResponseEntity.ok().body(Map.of("success", result));
//    }

//    @PostMapping("/api/auth/forgot-password")
//    @ResponseBody
//    public ResponseEntity<?> forgotPassword(@RequestParam String email) {
//        log.info("Start processing forgot password for: {}", email);
//
//        try {
//            // 1. Простая проверка email
//            if (email == null || email.isEmpty()) {
//                log.warn("Empty email received");
//                return ResponseEntity.badRequest().body(Map.of("success", false, "message", "Email не может быть пустым"));
//            }
//
//            // 2. Тестовая генерация токена
//            String testToken = "test-token-" + System.currentTimeMillis();
//            log.info("Generated test token: {}", testToken);
//
//            String resetToken = clientAuthService.generatePasswordResetToken(email);
//            log.info("Real token generated: {}", resetToken);
//
//            // 3. Тестовая отправка (без реального вызова сервиса)
//            log.info("Test email sending to: {}", email);
//            try {
//                boolean emailSent = brevoService.sendPasswordResetEmail(email, resetToken);
//                if (!emailSent) {
//                    throw new EmailSendingException("Email service returned false");
//                }
//            } catch (Exception e) {
//                log.error("Email sending failed", e);
//                throw e;
//            }
//
//            return ResponseEntity.ok().body(Map.of(
//                    "success", true,
//                    "message", "Тестовый режим: токен " + testToken + " сгенерирован для " + email
//            ));
//
//        } catch (Exception e) {
//            log.error("Error in forgotPassword endpoint", e);
//            return ResponseEntity.internalServerError().body(Map.of(
//                    "success", false,
//                    "message", "Тестовая ошибка: " + e.getClass().getSimpleName()
//            ));
//        }
//    }

//     API для восстановления пароля
    @PostMapping("/api/auth/forgot-password")
    @ResponseBody
    public ResponseEntity<?> forgotPassword(@RequestParam String email) {
        try {
            System.out.println("Получен запрос на восстановление пароля для email: " + email);

            // 1. Проверка формата email
            if (!email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
                System.out.println("Неверный формат email: " + email);
                return ResponseEntity.badRequest().body(Map.of(
                        "success", false,
                        "message", "Неверный формат email"
                ));
            }

            // 2. Генерация токена
            String resetToken = clientAuthService.generatePasswordResetToken(email);
            System.out.println("Сгенерирован токен для email " + email + ": " + resetToken);

            // 3. Отправка email
            System.out.println("Попытка отправки письма...");
            boolean emailSent = brevoService.sendPasswordResetEmail(email, resetToken);

            if (emailSent) {
                System.out.println("Письмо успешно отправлено на " + email);
                return ResponseEntity.ok().body(Map.of(
                        "success", true,
                        "message", "Инструкции по восстановлению пароля отправлены на ваш email"
                ));
            } else {
                System.out.println("Не удалось отправить письмо на " + email);
                throw new EmailSendingException("Не удалось отправить письмо");
            }
        } catch (ClientNotFoundException e) {
            System.out.println("Пользователь не найден: " + email);
            return ResponseEntity.badRequest().body(Map.of(
                    "success", false,
                    "message", "Пользователь с таким email не найден"
            ));
        } catch (EmailSendingException e) {
            System.out.println("Ошибка отправки письма: " + e.getMessage());
            return ResponseEntity.status(500).body(Map.of(
                    "success", false,
                    "message", "Ошибка при отправке письма. Пожалуйста, попробуйте позже."
            ));
        } catch (TooManyResetAttemptsException e) {
            System.out.println("Слишком много попыток для email: " + email);
            return ResponseEntity.status(429).body(Map.of(
                    "success", false,
                    "message", "Слишком много запросов. Пожалуйста, попробуйте позже."
            ));
        } catch (Exception e) {
            System.out.println("Неожиданная ошибка: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(500).body(Map.of(
                    "success", false,
                    "message", "Внутренняя ошибка сервера: " + e.getMessage()
            ));
        }
    }

    @GetMapping("/reset-password")
    public String showResetPasswordPage(@RequestParam String token, Model model) {
        try {
            clientAuthService.validatePasswordResetToken(token);
            model.addAttribute("token", token);
            model.addAttribute("resetPasswordRequest", new ResetPasswordRequest());
            return "reset-password";
        } catch (InvalidTokenException e) {
            model.addAttribute("error", "Недействительная или просроченная ссылка");
            return "reset-password-error";
        }
    }

    @PostMapping("/reset-password")
    public String resetPassword(
            @Valid @ModelAttribute("resetPasswordRequest") ResetPasswordRequest request,
            BindingResult result,
            @RequestParam String token,
            RedirectAttributes redirectAttributes
    ) {
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.resetPasswordRequest", result);
            redirectAttributes.addFlashAttribute("resetPasswordRequest", request);
            return "redirect:/reset-password?token=" + token;
        }

        if (!request.getNewPassword().equals(request.getConfirmPassword())) {
            redirectAttributes.addFlashAttribute("error", "Пароли не совпадают");
            return "redirect:/reset-password?token=" + token;
        }

        try {
            clientAuthService.resetPassword(token, request.getNewPassword());
            redirectAttributes.addFlashAttribute("success", "Пароль успешно изменен");
            return "redirect:/login";
        } catch (InvalidTokenException e) {
            redirectAttributes.addFlashAttribute("error", "Недействительная или просроченная ссылка");
            return "redirect:/reset-password-error";
        }
    }
}
