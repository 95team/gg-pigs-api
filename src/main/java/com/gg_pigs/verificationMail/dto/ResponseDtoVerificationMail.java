package com.gg_pigs.verificationMail.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Builder
@Getter
public class ResponseDtoVerificationMail {

    private String sender;
    private String receiver;
    private String verificationCode;
    private boolean status;

    public ResponseDtoVerificationMail() {
        this.sender = null;
        this.receiver = null;
        this.verificationCode = null;
        this.status = false;
    }

    public void changeToSuccess(String sender, String receiver, String verificationCode) {
        this.sender = sender;
        this.receiver = receiver;
        this.verificationCode = verificationCode;
        this.status = true;
    }
}
