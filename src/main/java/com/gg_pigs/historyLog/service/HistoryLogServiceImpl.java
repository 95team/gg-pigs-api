package com.gg_pigs.historyLog.service;

import com.gg_pigs._common.enums.HistoryLogAction;
import com.gg_pigs.historyLog.entity.HistoryLog;
import com.gg_pigs.historyLog.repository.HistoryLogRepository;
import com.gg_pigs.historyLogType.entity.HistoryLogType;
import com.gg_pigs.historyLogType.repository.HistoryLogTypeRepository;
import com.gg_pigs.user.entity.User;
import com.gg_pigs.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class HistoryLogServiceImpl implements HistoryLogService {

    private final UserRepository userRepository;
    private final HistoryLogRepository historyLogRepository;
    private final HistoryLogTypeRepository historyLogTypeRepository;

    @Override
    public boolean writeHistoryLog(HistoryLogAction type, User worker, String title, String content, boolean isSuccessful) {
        boolean resultOfWritingLog = false;

        HistoryLogType historyLogType = this.findHistoryLogTypeByType(type.name());

        HistoryLog historyLog = HistoryLog
                .builder()
                .title(title)
                .content(content)
                .user(worker)
                .historyLogType(historyLogType)
                .isSuccessful(isSuccessful ? 'Y' : 'N')
                .build();

        try {
            historyLogRepository.save(historyLog);
            resultOfWritingLog = true;
        } catch (Exception exception) {
            this.failedToWriteHistoryLog();
            this.failedToWriteHistoryLog(exception.getMessage());
            this.failedToWriteHistoryLog(type, worker.getEmail(), title, content);
        }

        return resultOfWritingLog;
    }

    private User findUserByEmail(String userEmail) {
        return userRepository.findUserByEmail(userEmail).orElse(null);
    }

    private HistoryLogType findHistoryLogTypeByType(String type) {
        return historyLogTypeRepository.findHistoryLogTypeByType(type).orElse(null);
    }

    private void failedToWriteHistoryLog() {
        System.out.println("HistoryLog 삽입에 실패했습니다.");
    }

    private void failedToWriteHistoryLog(String message) {
        System.out.println("History Log message: " + message);
    }

    private void failedToWriteHistoryLog(HistoryLogAction type, String email, String title, String content) {
        System.out.println("History Log type: " + type.name());
        System.out.println("History Log email: " + email);
        System.out.println("History Log title: " + title);
        System.out.println("History Log content: " + content);
    }
}
