package com.pangoapi.verificationMail.service;

import com.pangoapi._common.utility.MailHandler;
import com.pangoapi.verificationMail.entity.VerificationMail;
import com.pangoapi.verificationMail.dto.RequestDtoVerificationMail;
import com.pangoapi.verificationMail.dto.ResponseDtoVerificationMail;
import com.pangoapi.verificationMail.repository.VerificationMailRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.naming.LimitExceededException;
import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class VerificationMailService {

    private final Environment environment;
    private final JavaMailSender javaMailSender;
    private final VerificationMailRepository verificationMailRepository;
    private Long maximumNumberOfRequests = 100L;

    /**
     * CREATE
     * */
    @Transactional
    public ResponseDtoVerificationMail sendVerificationEmail(RequestDtoVerificationMail requestDtoVerificationMail) throws Exception {
        if(!VerificationMail.checkEmailFormat(requestDtoVerificationMail.getReceiver())) {
            throw new IllegalArgumentException("적절하지 않은 이메일 형식 입니다. (Please check the email)");
        }
        if(verificationMailRepository.countByToEmailAndSentDate(requestDtoVerificationMail.getReceiver(), LocalDate.now()) >= maximumNumberOfRequests) {
            throw new LimitExceededException("일일 API 요청 횟수를 초과했습니다. (Exceeded 5 API requests)");
        }

        String toEmail = requestDtoVerificationMail.getReceiver();
        String fromEmail = environment.getProperty("application.mail.from");
        String verificationCode = VerificationMail.makeVerificationCode();
        String subject = VerificationMail.makeSubject();
        String content = VerificationMail.makeContent(verificationCode);

        Long verificationMailId = verificationMailRepository.save(VerificationMail.createVerificationMail(toEmail, fromEmail, subject, content, verificationCode)).getId();
        VerificationMail sentVerificationMail = verificationMailRepository.findById(verificationMailId).orElseThrow(() -> new EntityNotFoundException("해당 데이터를 조회할 수 없습니다."));

        ResponseDtoVerificationMail responseDtoVerificationMail = new ResponseDtoVerificationMail();

        try {
            MailHandler mailHandler = new MailHandler(javaMailSender);
            mailHandler.setFrom(fromEmail);
            mailHandler.setTo(toEmail);
            mailHandler.setSubject(subject);
            mailHandler.setText(content, true);
            mailHandler.send();

            sentVerificationMail.changeStatusToSuccess();
            responseDtoVerificationMail.changeToSuccess(fromEmail, toEmail, verificationCode);
        } catch (Exception exception) {
            System.out.println(exception);
            sentVerificationMail.changeStatusToFailure();
            throw new Exception("인증메일 전송을 실패하였습니다.");
        }

        return responseDtoVerificationMail;
    }
}
