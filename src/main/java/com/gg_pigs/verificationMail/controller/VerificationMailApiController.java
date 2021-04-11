package com.gg_pigs.verificationMail.controller;

import com.gg_pigs._common.dto.ApiResponse;
import com.gg_pigs.verificationMail.dto.RequestDtoVerificationMail;
import com.gg_pigs.verificationMail.dto.ResponseDtoVerificationMail;
import com.gg_pigs.verificationMail.service.VerificationMailService;
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
        ResponseDtoVerificationMail responseDtoVerificationMail = verificationMailService.send4EmailVerification(requestDtoVerificationMail);

        return new ApiResponse(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), responseDtoVerificationMail);
    }
}
