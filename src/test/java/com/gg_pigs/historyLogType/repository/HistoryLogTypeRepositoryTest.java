package com.gg_pigs.historyLogType.repository;

import com.gg_pigs._common.enums.HistoryLogAction;
import com.gg_pigs.historyLogType.entity.HistoryLogType;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

@DataJpaTest
class HistoryLogTypeRepositoryTest {

    @Autowired TestEntityManager entityManager;
    @Autowired HistoryLogTypeRepository historyLogTypeRepository;

    @BeforeEach
    void setUp() {
        for (HistoryLogAction type : HistoryLogAction.values()) {
            entityManager.persist(new HistoryLogType(null, type.name()));
        }
    }

    @Test
    void findHistoryLogTypeByType() {
        // Given // When
        HistoryLogType createType = historyLogTypeRepository.findHistoryLogTypeByType(HistoryLogAction.CREATE.name()).orElse(null);
        HistoryLogType readType = historyLogTypeRepository.findHistoryLogTypeByType(HistoryLogAction.READ.name()).orElse(null);
        HistoryLogType updateType = historyLogTypeRepository.findHistoryLogTypeByType(HistoryLogAction.UPDATE.name()).orElse(null);
        HistoryLogType deleteType = historyLogTypeRepository.findHistoryLogTypeByType(HistoryLogAction.DELETE.name()).orElse(null);

        // Then
        Assertions.assertThat(createType.getType()).isEqualTo(HistoryLogAction.CREATE.name());
        Assertions.assertThat(readType.getType()).isEqualTo(HistoryLogAction.READ.name());
        Assertions.assertThat(updateType.getType()).isEqualTo(HistoryLogAction.UPDATE.name());
        Assertions.assertThat(deleteType.getType()).isEqualTo(HistoryLogAction.DELETE.name());
    }
}