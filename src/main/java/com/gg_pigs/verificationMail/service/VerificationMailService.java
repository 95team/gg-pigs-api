package com.gg_pigs.verificationMail.service;

import com.gg_pigs.verificationMail.dto.RequestDtoVerificationMail;
import com.gg_pigs.verificationMail.dto.ResponseDtoVerificationMail;

public interface VerificationMailService {

    /**
     * '이메일 인증' 을 위한 인증 메일 전송
     * */
    ResponseDtoVerificationMail send4EmailVerification(RequestDtoVerificationMail requestDtoVerificationMail) throws Exception;
}
