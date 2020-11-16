package com.pangoapi.verificationMail.controller;

import com.pangoapi._common.dto.ApiResponse;
import com.pangoapi.verificationMail.dto.RequestDtoVerificationMail;
import com.pangoapi.verificationMail.dto.ResponseDtoVerificationMail;
import com.pangoapi.verificationMail.service.VerificationMailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class VerificationMailApiController {

    private final VerificationMailService verificationMailService;

    @PostMapping("/api/v1/verification-mails")
    public ApiResponse sendVerificationEmail(@RequestBody RequestDtoVerificationMail requestDtoVerificationMail) throws Exception {
        ResponseDtoVerificationMail responseDtoVerificationMail = verificationMailService.sendVerificationEmail(requestDtoVerificationMail);

        return new ApiResponse(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), responseDtoVerificationMail);
    }
}
