package com.alkemy.disney.security.service;

import com.alkemy.disney.exception.msg.ExceptionMsg;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class EmailService {

    @Value("${app.sendgrid.key}")
    private String appKey;

    @Value("${app.sendgrid.mail.sender}")
    private String mail;

    public void sendWelcomeEmailTo(String username) throws IOException {

        Email mailSender = new Email(mail);
        Email mailReceiver = new Email(username);

        String subject = "Alkemy Challenge BackEnd Java";
        Content content = new Content("text/plain",
                "Successful registration to Matias Espinola's Disney API");

        Mail mail = new Mail(mailSender, subject, mailReceiver, content);
        SendGrid sendGrid = new SendGrid(appKey);
        Request request = new Request();

        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            Response response = sendGrid.api(request);
            System.out.println("Email sent. Status: " + response.getStatusCode());

        } catch (IOException e) {
            throw new IOException(ExceptionMsg.SEND_EMAIL_FAIL);
        }

    }
}
