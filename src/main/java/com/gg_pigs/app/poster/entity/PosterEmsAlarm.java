package com.gg_pigs.app.poster.entity;

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
public class PosterEmsAlarm implements EmailAlarm {
    private String subject;
    private String message;
    private String from;
    private List<String> to;
    private List<String> cc;
    private List<String> bcc;

    public static PosterEmsAlarm getInstanceForCreate(Poster poster) {
        String subject = "[GG-PIGS] (PosterRequestEmsAlarm) 포스터 등록 요청이 접수되었습니다.";
        String message = String.format("[GG-PIGS] (PosterRequestEmsAlarm) ID : %d | TITLE : %s", poster.getId(), poster.getTitle());

        return PosterEmsAlarm.builder()
                .subject(subject)
                .message(message)
                .build();
    }
}
