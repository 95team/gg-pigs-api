package com.pangoapi.historyLog.entity;

import com.pangoapi.historyLogType.entity.HistoryLogType;
import com.pangoapi.user.entity.User;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Getter
@Entity
public class HistoryLog {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "history_log_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "history_log_type_id")
    private HistoryLogType historyLogType;

    @Column(length = 128)
    private String title;

    @Column(length = 512)
    private String content;

    private char isSuccessful;
}
