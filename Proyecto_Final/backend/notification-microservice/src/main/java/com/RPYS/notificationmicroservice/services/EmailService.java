package com.RPYS.notificationmicroservice.services;

import com.RPYS.notificationmicroservice.models.Email;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.nio.charset.StandardCharsets;

@Service
@EnableAsync
public class EmailService {
    private final JavaMailSender emailSender;
    private final SpringTemplateEngine templateEngine;

    public EmailService(@Qualifier("javaMailSender") JavaMailSender emailSender, SpringTemplateEngine templateEngine) {
        this.emailSender = emailSender;
        this.templateEngine = templateEngine;
    }

    @Async
    public void sendTemplateEmail(Email email, String template, File file) {
        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper;

        try {
            email.setFrom("prestalialoans@gmail.com");
            helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                    StandardCharsets.UTF_8.name());
            Context context = new Context();
            context.setVariable("data", email);
            String htmlContent = templateEngine.process(template, context);
            if(file != null){
                helper.addAttachment(file.getName(),file);
            }
            helper.setTo(email.getTo());
            helper.setText(htmlContent, true);
            helper.setSubject(email.getTitle());
            helper.setFrom(email.getFrom());

            emailSender.send(message);
            if(file.delete())
            {
                System.out.println("File deleted successfully");
            }
            else
            {
                System.out.println("Failed to delete the file");
            }
        } catch (MessagingException e) {
            e.printStackTrace();
        }

    }

}
