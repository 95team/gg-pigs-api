package com.gg_pigs.app.verificationMail.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class RequestDtoVerificationMail {

    private String receiver;
}
