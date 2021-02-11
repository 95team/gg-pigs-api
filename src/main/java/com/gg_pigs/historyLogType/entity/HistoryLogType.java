package com.gg_pigs.historyLogType.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class HistoryLogType {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "history_log_type_id")
    private Long id;

    @Column(length = 32)
    private String type;
}
