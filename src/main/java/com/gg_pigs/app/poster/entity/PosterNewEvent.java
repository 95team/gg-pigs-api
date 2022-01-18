package com.gg_pigs.app.poster.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PosterNewEvent {
    Poster poster;
    PosterEmsAlarm posterEmsAlarm;

    public static PosterNewEvent of(Poster poster) {
        return PosterNewEvent.builder()
                .poster(poster)
                .posterEmsAlarm(PosterEmsAlarm.getInstanceForCreate(poster))
                .build();
    }
}
