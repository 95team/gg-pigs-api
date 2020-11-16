package com.pangoapi._common.utility;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

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

    public void setText(String text) throws MessagingException {
        mimeMessageHelper.setText(text, true);
    }

    public void setText(String text, boolean useHtml) throws MessagingException {
        mimeMessageHelper.setText(text, useHtml);
    }

    public void setMailHandler(String from, String to, String subject, String text) throws MessagingException {
        this.setFrom(from);
        this.setTo(to);
        this.setSubject(subject);
        this.setText(text);
    }

    public void send() {
        try {
            javaMailSender.send(mimeMessage);
        }catch(Exception exception) {
            throw exception;
        }
    }
}
