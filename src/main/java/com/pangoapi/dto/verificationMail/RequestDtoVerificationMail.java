package com.pangoapi.dto.verificationMail;

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
