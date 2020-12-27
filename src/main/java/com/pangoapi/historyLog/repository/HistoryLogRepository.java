package com.pangoapi.historyLog.repository;

import com.pangoapi.historyLog.entity.HistoryLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HistoryLogRepository extends JpaRepository<HistoryLog, Long> {
}
