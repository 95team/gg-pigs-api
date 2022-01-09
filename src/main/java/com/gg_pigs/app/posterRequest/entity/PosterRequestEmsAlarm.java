package com.gg_pigs.app.posterRequest.entity;

import com.gg_pigs.modules.ems.domain.EmailAlarm;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PosterRequestEmsAlarm implements EmailAlarm {
    private String subject;
    private String message;
    private String from;
    private List<String> to;
    private List<String> cc;
    private List<String> bcc;

    public static PosterRequestEmsAlarm getInstanceForCreate(PosterRequest pr) {
        String subject = "[GG-PIGS] (PosterRequestEmsAlarm) 포스터 등록 요청이 접수되었습니다.";
        String message = String.format("[GG-PIGS] (PosterRequestEmsAlarm) ID : %d | TITLE : %s", pr.getId(), pr.getTitle());

        return PosterRequestEmsAlarm.builder()
                .subject(subject)
                .message(message)
                .build();
    }
}
