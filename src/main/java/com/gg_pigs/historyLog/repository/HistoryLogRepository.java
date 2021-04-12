package com.gg_pigs.historyLog.repository;

import com.gg_pigs.historyLog.entity.HistoryLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HistoryLogRepository extends JpaRepository<HistoryLog, Long> {
}
