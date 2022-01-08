package com.gg_pigs.app.historyLog.repository;

import com.gg_pigs.app.historyLog.entity.HistoryLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HistoryLogRepository extends JpaRepository<HistoryLog, Long> {
}
