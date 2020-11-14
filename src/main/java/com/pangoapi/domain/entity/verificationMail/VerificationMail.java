package com.pangoapi.domain.entity.verificationMail;

import com.pangoapi.common.CommonDefinition;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Getter
@Entity
public class VerificationMail {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "verification_mail_id")
    private Long id;

    private String toEmail;
    private String fromEmail;
    private String verificationCode;
    private String status;
    private String subject;
    private String content;
    private LocalDate sentDate;

    public void changeStatusToSuccess() {
        this.status = "SUCCESS";
    }

    public static Boolean checkEmailFormat(String email) {
        boolean result = false;

        Pattern pattern = CommonDefinition.ALLOWABLE_EMAIL_FORMAT_PATTERN;
        Matcher matcher = pattern.matcher(email);

        if(matcher.find()) { result = true; }

        return result;
    }

    public static String makeSubject() {
        return "인증코드를 입력해주세요!";
    }

    public static String makeContent(String verificationCode) {
        String content =
                "<!DOCTYPE html>\n" +
                        "<html lang=\"en\">\n" +
                        "<head>\n" +
                        "    <meta charset=\"UTF-8\">\n" +
                        "    <title>인증코드를 입력해주세요!</title>\n" +
                        "</head>\n" +
                        "<body>\n" +
                        "    <h3>서비스를 이용하기 위해 아래의 '인증코드'를 입력해주세요.</h3>\n" +
                        "    <h2>" + verificationCode + "</h2>\n" +
                        "    <h3>감사합니다.</h3>\n" +
                        "</body>\n" +
                        "</html>";

        return content;
    }

    public static String makeVerificationCode() {
        String verificationCode = "620124";
        verificationCode = String.valueOf(System.currentTimeMillis() % 1000000);

        return verificationCode;
    }

    public static VerificationMail createVerificationMail(String toEmail, String fromEmail, String subject, String content) {
        String status = "WAITING";
        String verificationCode = makeVerificationCode();
        LocalDate sentDate = LocalDate.now();

        return VerificationMail.builder()
                .id(null)
                .toEmail(toEmail)
                .fromEmail(fromEmail)
                .subject(subject)
                .content(content)
                .verificationCode(verificationCode)
                .status(status)
                .sentDate(sentDate)
                .build();
    }
}
