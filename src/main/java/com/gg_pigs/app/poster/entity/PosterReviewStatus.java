package com.gg_pigs.app.poster.entity;

import java.util.HashMap;
import java.util.Map;

public enum PosterReviewStatus {
    NEW, APPROVAL, NON_APPROVAL, PENDING;

    private static final Map<String, PosterReviewStatus> map = new HashMap<>();
    static {
        for (PosterReviewStatus posterReviewStatus :PosterReviewStatus.values()) {
            map.put(posterReviewStatus.name(), posterReviewStatus);
        }
    }

    public static PosterReviewStatus findByName(String name) {
        return map.getOrDefault(name, null);
    }
}
