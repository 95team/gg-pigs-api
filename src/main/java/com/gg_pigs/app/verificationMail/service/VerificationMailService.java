package com.gg_pigs.app.verificationMail.service;

import com.gg_pigs.global.utility.MailHandler;
import com.gg_pigs.app.user.repository.UserRepository;
import com.gg_pigs.app.verificationMail.entity.VerificationMail;
import com.gg_pigs.app.verificationMail.dto.RequestDtoVerificationMail;
import com.gg_pigs.app.verificationMail.dto.ResponseDtoVerificationMail;
import com.gg_pigs.app.verificationMail.repository.VerificationMailRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.dao.DataIntegrityViolationException;
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
    private final UserRepository userRepository;
    private final VerificationMailRepository verificationMailRepository;

    private Long maximumNumberOfRequests = 100L;

    protected ResponseDtoVerificationMail makeResponseDtoVerificationMail() {
        return new ResponseDtoVerificationMail();
    }

    protected MailHandler makeMailHandler(JavaMailSender javaMailSender) throws Exception {
        return new MailHandler(javaMailSender);
    }

    /** CREATE */
    @Transactional
    public ResponseDtoVerificationMail send4EmailVerification(RequestDtoVerificationMail requestDtoVerificationMail) throws Exception {
        if(requestDtoVerificationMail.getReceiver() == null) {
            throw new IllegalArgumentException("적절하지 않은 요청입니다. (Please check the required value)");
        }
        if(!VerificationMail.checkEmailFormat(requestDtoVerificationMail.getReceiver())) {
            throw new IllegalArgumentException("적절하지 않은 이메일 형식 입니다. (Please check the email)");
        }
        if(verificationMailRepository.countByToEmailAndSentDate(requestDtoVerificationMail.getReceiver(), LocalDate.now()) >= maximumNumberOfRequests) {
            throw new LimitExceededException("일일 API 요청 횟수를 초과했습니다. (Exceeded 5 API requests)");
        }
        if(userRepository.countByEmail(requestDtoVerificationMail.getReceiver()) >= 1) {
            throw new DataIntegrityViolationException("이미 사용 중인 이메일입니다. (Please check the email.)");
        }

        ResponseDtoVerificationMail responseDtoVerificationMail = this.makeResponseDtoVerificationMail();
        MailHandler mailHandler = this.makeMailHandler(javaMailSender);

        String toEmail = requestDtoVerificationMail.getReceiver();
        String fromEmail = environment.getProperty("application.mail.from");
        String verificationCode = VerificationMail.makeVerificationCode();
        String subject = VerificationMail.makeSubject();
        String content = VerificationMail.makeContent(verificationCode);

        Long verificationMailId = verificationMailRepository.save(VerificationMail.createVerificationMail(fromEmail, toEmail, subject, content, verificationCode)).getId();
        VerificationMail sentVerificationMail = verificationMailRepository.findById(verificationMailId).orElseThrow(() -> new EntityNotFoundException("해당 데이터를 조회할 수 없습니다."));
        try {
            mailHandler.setMail(fromEmail, toEmail, subject, content);
            mailHandler.send();

            sentVerificationMail.changeStatusToSuccess();
            responseDtoVerificationMail.changeToSuccess(fromEmail, toEmail, verificationCode);
        } catch (Exception exception) {
            exception.printStackTrace();
            sentVerificationMail.changeStatusToFailure();
            throw new Exception("인증메일 전송을 실패하였습니다.");
        }

        return responseDtoVerificationMail;
    }
}
