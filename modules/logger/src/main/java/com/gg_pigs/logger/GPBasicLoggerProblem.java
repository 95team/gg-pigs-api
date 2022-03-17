//package com.gg_pigs.logger;
//
//import lombok.extern.slf4j.Slf4j;
//
//@Slf4j
//public class GPBasicLoggerProblem implements GPLogger {
//
//    private static final String START_PREFIX = "-->";
//    private static final String COMPLETE_PREFIX = "<--";
//    private static final String EX_PREFIX = "<X-";
//
//    private GPBasicLog logHolder;
//
//    @Override
//    public GPBasicLog begin(String message) {
//        initLog(message);
//        GPBasicLog basicLog = logHolder;
//
//        Long startTimeMs = System.currentTimeMillis();
//        log.info("[{}] {}{}", basicLog.getUuid(), addSpace(START_PREFIX, basicLog.getDepth()), basicLog.getMessage());
//        return basicLog;
//    }
//
//    @Override
//    public void end(GPBasicLog basicLog) {
//        complete(basicLog, null);
//    }
//
//    @Override
//    public void exception(GPBasicLog basicLog, Exception e) {
//        complete(basicLog, e);
//    }
//
//    private void initLog(String message) {
//        GPBasicLog basicLog = logHolder;
//
//        if(basicLog == null) {
//            logHolder = GPBasicLog.newLog(message);
//            return;
//        }
//
//        logHolder = basicLog.nextLog(message);
//    }
//
//    private void releaseLog() {
//        GPBasicLog basicLog = logHolder;
//        if(basicLog.isFirstLog()) {
//            logHolder = null;
//            return;
//        }
//
//        logHolder = basicLog.previousLog();
//    }
//
//    private void complete(GPBasicLog basicLog, Exception e) {
//        String uuid = basicLog.getUuid();
//        String message = basicLog.getMessage();
//        int depth = basicLog.getDepth();
//        long processedTimeMs = System.currentTimeMillis() - basicLog.getStartTimeMs();
//
//        if (e == null) {
//            log.info("[{}] {}{} time={}ms", uuid, addSpace(COMPLETE_PREFIX, depth), message, processedTimeMs);
//        } else {
//            log.info("[{}] {}{} time={}ms ex={}", uuid, addSpace(EX_PREFIX, depth), message, processedTimeMs, e.toString());
//        }
//
//        releaseLog();
//    }
//
//    private static String addSpace(String prefix, int level) {
//        StringBuilder sb = new StringBuilder();
//        for (int i = 0; i < level; i++) {
//            sb.append((i == level - 1) ? "|" + prefix : "|   ");
//        }
//        return sb.toString();
//    }
//
//}
