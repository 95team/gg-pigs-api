package com.gg_pigs.app.historyLog.service;

import com.gg_pigs.app.historyLog.entity.HistoryLog;
import com.gg_pigs.app.historyLog.repository.HistoryLogRepository;
import com.gg_pigs.app.user.entity.User;
import com.gg_pigs.app.user.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
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

    @Autowired
    HistoryLogService historyLogService;

    @MockBean UserRepository userRepository;
    @MockBean HistoryLogRepository historyLogRepository;

    @Mock User HistoryLogUser;

    @Test
    public void When_call_writeHistoryLog_Then_call_save() {
        // Given
        String email = "pigs95team@gmail.com";
        String title = "This is a title.";
        String content = "This is a content.";

        // When
        boolean resultOfWritingLog = historyLogService.writeHistoryLog(HistoryLog.HistoryLogType.CREATE, HistoryLogUser, title, content, true);

        // Then
        Assertions.assertThat(resultOfWritingLog).isTrue();
        Mockito.verify(historyLogRepository, Mockito.times(1)).save(any(HistoryLog.class));
    }
}