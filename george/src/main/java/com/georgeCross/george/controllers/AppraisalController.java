package com.georgeCross.george.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:5173")
@RequiredArgsConstructor
public class AppraisalController {

    private final String TELEGRAM_TOKEN = "";
    private final String CHAT_ID = "";

    @PostMapping(value = "/appraisal", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> sendToTelegram(
            @RequestParam("name") String name,
            @RequestParam("phone") String phone,
            @RequestParam(value = "comment", required = false) String comment,
            @RequestParam("file") MultipartFile[] files) {

        try {
            RestTemplate restTemplate = new RestTemplate();

            // Формируем текст сообщения для эксперта «Аквилон»
            String caption = String.format(
                    "🔔 НОВАЯ ЗАЯВКА НА ОЦЕНКУ\n\n👤 Имя: %s\n📞 Телефон: %s\n💬 Комментарий: %s",
                    name, phone, (comment != null && !comment.trim().isEmpty() ? comment : "нет")
            );

            // Сценарий 1: Отправка одной фотографии
            if (files != null && files.length == 1) {
                // ИСПРАВЛЕНО: Безопасная сборка URI через UriComponentsBuilder
                URI uri = UriComponentsBuilder.fromHttpUrl("https://api.telegram.org")
                        .path("/bot" + TELEGRAM_TOKEN + "/sendPhoto")
                        .build()
                        .toUri();

                MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
                body.add("chat_id", CHAT_ID);
                body.add("caption", caption);
                body.add("photo", files[0].getResource());

                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.MULTIPART_FORM_DATA);

                restTemplate.postForEntity(uri, new HttpEntity<>(body, headers), String.class);
            }
            // Сценарий 2: Отправка нескольких фотографий (Альбом/Пакет)
            else if (files != null && files.length > 1) {

                // 1. Отправляем текстовые данные клиента (sendMessage)
                URI textUri = UriComponentsBuilder.fromHttpUrl("https://api.telegram.org")
                        .path("/bot" + TELEGRAM_TOKEN + "/sendMessage")
                        .queryParam("chat_id", CHAT_ID)
                        .queryParam("text", caption)
                        .build()
                        .toUri();

                restTemplate.getForObject(textUri, String.class);

                // 2. Поочередно отправляем все прикрепленные фотографии в этот же чат
                URI photoUri = UriComponentsBuilder.fromHttpUrl("https://api.telegram.org")
                        .path("/bot" + TELEGRAM_TOKEN + "/sendPhoto")
                        .build()
                        .toUri();

                for (MultipartFile file : files) {
                    if (file == null || file.isEmpty()) continue;

                    MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
                    body.add("chat_id", CHAT_ID);
                    body.add("photo", file.getResource());

                    HttpHeaders headers = new HttpHeaders();
                    headers.setContentType(MediaType.MULTIPART_FORM_DATA);

                    restTemplate.postForEntity(photoUri, new HttpEntity<>(body, headers), String.class);
                }
            }

            return ResponseEntity.ok().body("{\"status\":\"success\"}");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Ошибка при отправке в Telegram");
        }
    }
}
