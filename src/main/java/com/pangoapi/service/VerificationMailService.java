package com.pangoapi.service;

import com.pangoapi.common.utility.MailHandler;
import com.pangoapi.domain.entity.verificationMail.VerificationMail;
import com.pangoapi.dto.verificationMail.RequestDtoVerificationMail;
import com.pangoapi.dto.verificationMail.ResponseDtoVerificationMail;
import com.pangoapi.repository.verificationMail.VerificationMailRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
import javax.naming.LimitExceededException;
import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class VerificationMailService {

    @Autowired private Environment environment;
    @Autowired private JavaMailSender javaMailSender;
    private final VerificationMailRepository verificationMailRepository;
    private Long maximumNumberOfRequests = 5L;

    /**
     * CREATE
     * */
    @Transactional
    public ResponseDtoVerificationMail sendVerificationEmail(RequestDtoVerificationMail requestDtoVerificationMail) throws MessagingException, LimitExceededException {
        if(!VerificationMail.checkEmailFormat(requestDtoVerificationMail.getReceiver())) {
            throw new IllegalArgumentException("적절하지 않은 이메일 형식 입니다. (Please check the email)");
        }
        if(verificationMailRepository.countByToEmailAndSentDate(requestDtoVerificationMail.getReceiver(), LocalDate.now()) >= maximumNumberOfRequests) {
            throw new LimitExceededException("일일 API 요청 횟수를 초과했습니다. (Exceeded 5 API requests)");
        }

        ResponseDtoVerificationMail responseDtoVerificationMail = new ResponseDtoVerificationMail();

        String toEmail = requestDtoVerificationMail.getReceiver();
        String fromEmail = environment.getProperty("application.mail.from");
        String verificationCode = VerificationMail.makeVerificationCode();
        String subject = VerificationMail.makeSubject();
        String content = VerificationMail.makeContent(verificationCode);
        Long verificationMailId = verificationMailRepository.save(VerificationMail.createVerificationMail(toEmail, fromEmail, subject, content)).getId();

        MailHandler mailHandler = new MailHandler(javaMailSender);
        mailHandler.setFrom(fromEmail);
        mailHandler.setTo(toEmail);
        mailHandler.setSubject(subject);
        mailHandler.setText(content, true);
        try {
            mailHandler.send();

            VerificationMail sentVerificationMail = verificationMailRepository.findById(verificationMailId).orElseThrow(() -> new EntityNotFoundException("해당 데이터를 조회할 수 없습니다."));
            sentVerificationMail.changeStatusToSuccess();
            responseDtoVerificationMail.changeToSuccess(fromEmail, toEmail, verificationCode);
        } catch (MailException exception) {
            System.out.println(exception);
        }

        return responseDtoVerificationMail;
    }
}
