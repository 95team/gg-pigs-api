package com.pangoapi.controller;

import com.pangoapi.dto.ApiResponse;
import com.pangoapi.dto.verificationMail.RequestDtoVerificationMail;
import com.pangoapi.dto.verificationMail.ResponseDtoVerificationMail;
import com.pangoapi.service.VerificationMailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.naming.LimitExceededException;
import java.io.IOException;

@RequiredArgsConstructor
@RestController
public class MailApiController {

    private final VerificationMailService verificationMailService;

    @PostMapping("/api/v1/verification-mails")
    public ApiResponse sendVerificationEmail(@RequestBody RequestDtoVerificationMail requestDtoVerificationMail) throws LimitExceededException, IOException {
        ResponseDtoVerificationMail responseDtoVerificationMail = verificationMailService.sendVerificationEmail(requestDtoVerificationMail);

        return new ApiResponse(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), responseDtoVerificationMail);
    }
}
