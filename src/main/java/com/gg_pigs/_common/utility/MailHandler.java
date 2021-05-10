package com.gg_pigs._common.utility;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

/**
 * [References]
 * 1. https://victorydntmd.tistory.com/342
 * 2. https://medium.com/@js230023/spring-boot-%EB%A9%94%EC%9D%BC-%EB%B3%B4%EB%82%B4%EA%B8%B0-f01751da4c02
 * */

public class MailHandler {

    private JavaMailSender javaMailSender;
    private MimeMessage mimeMessage;
    private MimeMessageHelper mimeMessageHelper;

    public MailHandler(JavaMailSender javaMailSender) throws MessagingException {
        this.javaMailSender = javaMailSender;
        mimeMessage = javaMailSender.createMimeMessage();
        mimeMessageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
    }

    public void setFrom(String from) throws MessagingException {
        mimeMessageHelper.setFrom(from);
    }

    public void setTo(String to) throws MessagingException {
        mimeMessageHelper.setTo(to);
    }

    public void setSubject(String subject) throws MessagingException {
        mimeMessageHelper.setSubject(subject);
    }

    public void setContent(String text) throws MessagingException {
        this.setContent(text, true);
    }

    public void setContent(String text, boolean useHtml) throws MessagingException {
        mimeMessageHelper.setText(text, useHtml);
    }

    public void setMailHandler(String from, String to, String subject, String content) throws MessagingException {
        this.setFrom(from);
        this.setTo(to);
        this.setSubject(subject);
        this.setContent(content);
    }

    public Address[] getFrom() throws MessagingException {
        return mimeMessage.getFrom();
    }

    public Address[] getTo() throws MessagingException {
        return mimeMessage.getRecipients(Message.RecipientType.TO);
    }

    public String getSubject() throws MessagingException {
        return mimeMessage.getSubject();
    }

    public String getContent() throws MessagingException, IOException {
        String content = new BufferedReader(new InputStreamReader(mimeMessage.getInputStream(), StandardCharsets.UTF_8)).lines().collect(Collectors.joining("\n"));

        return content;
    }

    public void send() {
        try {
            javaMailSender.send(mimeMessage);
        }catch(Exception exception) {
            throw exception;
        }
    }
}
