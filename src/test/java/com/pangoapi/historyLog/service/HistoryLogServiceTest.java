package com.pangoapi.historyLog.service;

import com.pangoapi._common.enums.HistoryLogAction;
import com.pangoapi.historyLog.entity.HistoryLog;
import com.pangoapi.historyLog.repository.HistoryLogRepository;
import com.pangoapi.historyLogType.entity.HistoryLogType;
import com.pangoapi.historyLogType.repository.HistoryLogTypeRepository;
import com.pangoapi.user.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.mockito.ArgumentMatchers.any;

@SpringBootTest(
        classes = {
                HistoryLogService.class,
        }
)
class HistoryLogServiceTest {

    @Autowired HistoryLogService historyLogService;

    @MockBean UserRepository userRepository;
    @MockBean HistoryLogRepository historyLogRepository;
    @MockBean HistoryLogTypeRepository historyLogTypeRepository;

    @Test
    public void When_call_writeHistoryLog_Then_call_save() {
        // Given
        String email = "pigs95team@gmail.com";
        String title = "This is a title.";
        String content = "This is a content.";

        // When
        boolean resultOfWritingLog = historyLogService.writeHistoryLog(HistoryLogAction.CREATE, email, title, content);

        // Then
        Assertions.assertThat(resultOfWritingLog).isTrue();
        Mockito.verify(historyLogRepository, Mockito.times(1)).save(any(HistoryLog.class));
    }
}