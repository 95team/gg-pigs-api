package com.pangoapi.historyLogType.repository;

import com.pangoapi.historyLogType.entity.HistoryLogType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface HistoryLogTypeRepository extends JpaRepository<HistoryLogType, Long> {

    Optional<HistoryLogType> findHistoryLogTypeByType(String type);

}
