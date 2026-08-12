package com.georgeCross.george.controllers;

import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = {"http://localhost:5173", "https://aquilon-antique.ru"})
@RequiredArgsConstructor
public class AppraisalController {

    @Autowired
    private final JavaMailSender mailSender;

    @PostMapping(value = "/appraisal", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> sendAppraisalEmail(
            @RequestParam("name") String name,
            @RequestParam("phone") String phone,
            @RequestParam(value = "comment", required = false) String comment,
            @RequestParam("file") MultipartFile[] files) {

        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setFrom("kozinandrey@mail.ru");
            helper.setTo("kozinandrey@mail.ru");
            helper.setSubject("🔔 НОВАЯ ЗАЯВКА НА ОЦЕНКУ ПРЕДМЕТОВ");

            String text = String.format(
                    "Поступила новая заявка с сайта Аквилон:\n\n👤 Имя клиента: %s\n📞 Телефон: %s\n💬 Комментарий: %s",
                    name, phone, (comment != null && !comment.trim().isEmpty() ? comment : "нет комментария")
            );
            helper.setText(text);


            if (files != null) {
                for (MultipartFile file : files) {
                    if (file == null || file.isEmpty()) continue;

                    // Берем оригинальное имя файла, если его нет — генерируем дефолтное
                    String fileName = file.getOriginalFilename() != null ? file.getOriginalFilename() : "photo.jpg";
                    helper.addAttachment(fileName, file);
                }
            }

            mailSender.send(message);

            return ResponseEntity.ok().body("{\"status\":\"success\"}");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"status\":\"error\", \"message\":\"Ошибка при отправке почты\"}");
        }
    }
}
