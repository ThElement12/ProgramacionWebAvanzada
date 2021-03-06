package com.RPYS.notificationmicroservice.services;

import com.RPYS.notificationmicroservice.entities.Email;
import com.RPYS.notificationmicroservice.models.Event;
import com.RPYS.notificationmicroservice.models.User;
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
import java.util.List;

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
    public void sendBroadcastEventEmail( List<User> users, Event event) {
        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper;
        Email email = new Email();
        try {
            email.setFrom("prestalialoans@gmail.com");
            email.setTitle("Creacion de nuevo evento");

            for (User user : users) {
                email.setTo(user.getMail());
                email.setFullName(user.getFullName());
                email.setMessage("Hola, ".concat(user.getFullName())
                        + " este correo es para informar que se acaba de crear un nuevo evento a nombre de: "
                        .concat(event.getUser().getFullName())
                        .concat(" el cual se estará celebrando desde el -> ")
                        .concat(event.getStartTime().toString().replace("T", " a las ")).concat(" hasta ")
                        .concat(event.getEndTime().toString().replace("T", " a las "))
                        .concat(" Los productos que se ocuparan ese dia para la celebracion del evento son los siguientes:")
                );

                helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                        StandardCharsets.UTF_8.name());
                Context context = new Context();
                context.setVariable("data", email);
                context.setVariable("name", "RYPS");
                context.setVariable("products", event.getProductRequests());
                String htmlContent = templateEngine.process("event-email", context);

                helper.setTo(email.getTo());
                helper.setText(htmlContent, true);
                helper.setSubject(email.getTitle());
                helper.setFrom(email.getFrom());

                emailSender.send(message);

            }


        } catch (MessagingException e) {
            e.printStackTrace();
        }

    }


    @Async
    public void sendNewEventEmail( Event event) {
        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper;
        Email email = new Email();
        try {
            email.setFrom("prestalialoans@gmail.com");
            email.setTitle("Gracias por preferirnos.");
            email.setTo(event.getUser().getMail());
            email.setFullName(event.getUser().getFullName());
            email.setMessage("Hola, ".concat(event.getUser().getFullName())
                    + " este correo es para informar que se acaba de crear un"
                    .concat(event.getPlan().getName().equalsIgnoreCase
                            ("boda")?"a "+event.getPlan().getName():" "+event.getPlan().getName())
                    .concat(event.getPlan().getName().equalsIgnoreCase
                            ("boda")?" la ":" el ").concat("cual se estara celebrando el -> ")
                    .concat(event.getStartTime().toString().replace("T", " a las ")).concat(" hasta ")
                    .concat(event.getEndTime().toString().replace("T", " a las "))
                    .concat(" Los productos que se ocuparan ese dia para la celebracion del evento son los siguientes:")
            );

            helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                    StandardCharsets.UTF_8.name());
            Context context = new Context();
            context.setVariable("data", email);
            context.setVariable("name", "RYPS");
            context.setVariable("products", event.getProductRequests());
            String htmlContent = templateEngine.process("event-email", context);

            helper.setTo(email.getTo());
            helper.setText(htmlContent, true);
            helper.setSubject(email.getTitle());
            helper.setFrom(email.getFrom());

            emailSender.send(message);

        } catch (
                MessagingException e) {
            e.printStackTrace();
        }

    }

    @Async
    public void sendNewUserEmail( User user) {
        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper;
        Email email = new Email();
        try {
            email.setFrom("prestalialoans@gmail.com");
            email.setTitle("Bienvenido a RYPS");
            email.setTo(user.getMail());
            email.setFullName(user.getFullName());
            email.setMessage("Hola, ".concat(user.getFullName())
                    + " este correo es para darle la bienvenida a Rove Yosef Party Supply, " +
                    "y agradecerle que confie en nosotros para la creacion de sus eventos."
            );

            helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                    StandardCharsets.UTF_8.name());
            Context context = new Context();
            context.setVariable("data", email);
            context.setVariable("name", "Rove Yosef Party Supply");
            String htmlContent = templateEngine.process("new-user-email", context);

            helper.setTo(email.getTo());
            helper.setText(htmlContent, true);
            helper.setSubject(email.getTitle());
            helper.setFrom(email.getFrom());

            emailSender.send(message);

        } catch (
                MessagingException e) {
            e.printStackTrace();
        }

    }



}
