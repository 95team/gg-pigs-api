package com.gg_pigs.verificationMail.entity;

import com.gg_pigs._common.enums.VerificationMailStatus;
import com.gg_pigs._common.utility.EmailUtil;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.File;
import java.io.IOException;
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

    @Column(length = 32)
    private String toEmail;

    @Column(length = 32)
    private String fromEmail;

    @Column(length = 16)
    private String verificationCode;

    @Column(length = 16)
    private String status;

    @Column(length = 32)
    private String subject;

    @Column(length = 2048)
    private String content;

    private LocalDate sentDate;

    public void changeStatusToSuccess() {
        this.status = VerificationMailStatus.SUCCESS.name();
    }

    public void changeStatusToFailure() {
        this.status = VerificationMailStatus.FAILURE.name();
    }

    public static Boolean checkEmailFormat(String email) {
        boolean result = false;

        Pattern pattern = EmailUtil.ALLOWABLE_EMAIL_FORMAT_PATTERN;
        Matcher matcher = pattern.matcher(email);

        if(matcher.find()) { result = true; }

        return result;
    }

    public static String makeSubject() {
        return "인증코드를 입력해주세요!";
    }

    public static String makeContent(String verificationCode) throws IOException {
        /**
         * [References]
         * 1. https://yeon-blog.tistory.com/4
         * */
        Document document = Jsoup.parse(new File("src/main/resources/templates/mails/verificationMailTemplate.html"), "UTF-8");
        document.getElementById("verificationCode").text(verificationCode);
        String content = document.html();

        return content;
    }

    public static String makeVerificationCode() {
        /**
         * [Note]
         * 1. VerificationCode 를 6자리로 랜덤하게 생성합니다.
         * */
        String verificationCode = "620124";
        verificationCode = String.valueOf(System.currentTimeMillis() % 1000000);
        while(verificationCode.length() < 6) {
            verificationCode += ((int)(Math.random() * 10));
        }

        return verificationCode;
    }

    public static VerificationMail createVerificationMail(String fromEmail, String toEmail, String subject, String content, String verificationCode) {
        String status = VerificationMailStatus.WAITING.name();
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
