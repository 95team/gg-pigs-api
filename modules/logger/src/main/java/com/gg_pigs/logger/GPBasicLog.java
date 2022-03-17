//package com.gg_pigs.logger;
//
//import lombok.AllArgsConstructor;
//import lombok.Builder;
//import lombok.Getter;
//
//import java.util.UUID;
//
//@AllArgsConstructor
//@Builder
//@Getter
//public class GPBasicLog {
//
//    private final String uuid;
//    private String message;
//    private int depth;
//    private long startTimeMs;
//
//    public static GPBasicLog newLog() {
//        return GPBasicLog.builder()
//                         .uuid(createId())
//                         .message(null)
//                         .depth(0)
//                         .startTimeMs(System.currentTimeMillis())
//                         .build();
//    }
//
//    public static GPBasicLog newLog(String message) {
//        return GPBasicLog.builder()
//                         .uuid(createId())
//                         .message(message)
//                         .depth(0)
//                         .startTimeMs(System.currentTimeMillis())
//                         .build();
//    }
//
//    public static GPBasicLog newLog(String message, long startTimeMs) {
//        return GPBasicLog.builder()
//                         .uuid(createId())
//                         .message(message)
//                         .depth(0)
//                         .startTimeMs(startTimeMs)
//                         .build();
//    }
//
//    public GPBasicLog nextLog() {
//        return this.nextLog(null, System.currentTimeMillis());
//    }
//
//    public GPBasicLog nextLog(String message) {
//        return this.nextLog(message, System.currentTimeMillis());
//    }
//
//    public GPBasicLog nextLog(String message, long startTimeMs) {
//        GPBasicLog gPBasicLog = GPBasicLog.builder()
//                                          .uuid(getUuid())
//                                          .message(message)
//                                          .depth(getDepth())
//                                          .startTimeMs(startTimeMs)
//                                          .build();
//        gPBasicLog.increaseDepth();
//        return gPBasicLog;
//    }
//
//    public GPBasicLog previousLog() {
//        return this.previousLog(null, System.currentTimeMillis());
//    }
//
//    public GPBasicLog previousLog(String message) {
//        return this.previousLog(message, System.currentTimeMillis());
//    }
//
//    public GPBasicLog previousLog(String message, long startTimeMs) {
//        GPBasicLog gPBasicLog = GPBasicLog.builder()
//                                          .uuid(getUuid())
//                                          .message(message)
//                                          .depth(getDepth())
//                                          .startTimeMs(startTimeMs)
//                                          .build();
//        gPBasicLog.decreaseDepth();
//        return gPBasicLog;
//    }
//
//    public boolean isFirstLog() {
//        return depth == 0;
//    }
//
//    private static String createId() {
//        return UUID.randomUUID().toString().substring(0, 8);
//    }
//
//    private void setMessage(String message) {
//        this.message = message;
//    }
//
//    private void setStartTimeMs(long startTimeMs) {
//        this.startTimeMs = startTimeMs;
//    }
//
//    private void increaseDepth() {
//        this.depth++;
//    }
//
//    private void decreaseDepth() {
//        this.depth--;
//    }
//}
