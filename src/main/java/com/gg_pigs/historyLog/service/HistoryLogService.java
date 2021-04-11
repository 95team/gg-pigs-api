package com.gg_pigs.historyLog.service;

import com.gg_pigs._common.enums.HistoryLogAction;
import com.gg_pigs.user.entity.User;

public interface HistoryLogService {

    /**
     * @return Result of writing log
     * */
    boolean writeHistoryLog(HistoryLogAction action, User worker, String title, String content, boolean isSuccessful);
}
