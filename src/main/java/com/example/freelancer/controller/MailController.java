package com.example.freelancer.controller;

import com.example.freelancer.dto.JobDTO;
import com.example.freelancer.dto.MailDTO;
import com.example.freelancer.mail.MyConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;


@Controller
public class MailController {

    public String sendHtmlEmail(MailDTO mailDTO, JavaMailSender emailSender) throws MessagingException {
        System.out.println(mailDTO.toString());
        if (mailDTO == null)
            return "Lỗi";
        MimeMessage message = emailSender.createMimeMessage();

        boolean multipart = true;

        MimeMessageHelper helper = new MimeMessageHelper(message, multipart, "utf-8");

        String htmlMsg = mailDTO.getBody(); //html

        message.setContent(htmlMsg, "text/html");

        helper.setTo(mailDTO.getReceiver());

        helper.setSubject(mailDTO.getTitle());


        emailSender.send(message);

        return "Email Sent!";
    }
}
