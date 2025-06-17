package ru.itis.horoscope.service;

import lombok.RequiredArgsConstructor;
import okhttp3.*;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.itis.horoscope.exception.EmailSendingException;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class BrevoService {
    private static final String BREVO_API_URL = "https://api.brevo.com/v3/smtp/email";

    @Value("${brevo.api.key}")
    private String apiKey;

    private final OkHttpClient httpClient = new OkHttpClient();

    public boolean sendPasswordResetEmail(String email, String resetToken) throws EmailSendingException {
        try {
            String resetUrl = "http://localhost:8081/reset-password?token=" + resetToken;

            JSONObject requestBody = new JSONObject();
            requestBody.put("sender", new JSONObject()
                    .put("name", "Horoscope Service")
                    .put("email", "garifullinaralina@gmail.com"));

            requestBody.put("to", new JSONArray()
                    .put(new JSONObject().put("email", email)));

            requestBody.put("subject", "Восстановление пароля");
            requestBody.put("htmlContent", "<p>Для сброса пароля перейдите по <a href=\"" +
                    resetUrl + "\">ссылке</a></p>");

            Request request = new Request.Builder()
                    .url(BREVO_API_URL)
                    .post(RequestBody.create(
                            requestBody.toString(),
                            MediaType.parse("application/json")))
                    .addHeader("accept", "application/json")
                    .addHeader("content-type", "application/json")
                    .addHeader("api-key", apiKey)
                    .build();

            try (Response response = httpClient.newCall(request).execute()) {
                if (!response.isSuccessful()) {
                    throw new EmailSendingException("Failed to send email: " + response.code());
                }
                return response.code() == 201;
            }
        } catch (Exception e) {
            throw new EmailSendingException("Error sending email: " + e.getMessage());
        }
    }
}