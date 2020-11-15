package com.pangoapi.verificationMail.controller;

import com.pangoapi._common.dto.ApiResponse;
import com.pangoapi._common.utility.MailHandler;
import com.pangoapi.verificationMail.dto.RequestDtoVerificationMail;
import com.pangoapi.verificationMail.dto.ResponseDtoVerificationMail;
import com.pangoapi.verificationMail.service.VerificationMailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class VerificationMailApiController {

    private final VerificationMailService verificationMailService;
    private final JavaMailSender javaMailSender;

    @PostMapping("/api/v1/verification-mails")
    public ApiResponse sendVerificationEmail(@RequestBody RequestDtoVerificationMail requestDtoVerificationMail) throws Exception {
        MailHandler mailHandler = new MailHandler(javaMailSender);
        ResponseDtoVerificationMail responseDtoVerificationMail = new ResponseDtoVerificationMail();

        responseDtoVerificationMail = verificationMailService.sendVerificationEmail(mailHandler, responseDtoVerificationMail, requestDtoVerificationMail);

        return new ApiResponse(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), responseDtoVerificationMail);
    }
}
