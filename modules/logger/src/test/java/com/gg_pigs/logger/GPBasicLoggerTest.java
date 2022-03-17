package com.gg_pigs.logger;

import org.junit.jupiter.api.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.jupiter.api.Assertions.*;

class GPBasicLoggerTest {

    GPBasicLogger gpBasicLogger = new GPBasicLogger();
    GPBasicLoggerProblem gpBasicLoggerProblem = new GPBasicLoggerProblem();

    @Test
    void begin() {
        GPBasicLog begin1 = gpBasicLogger.begin("1");
        GPBasicLog begin2 = gpBasicLogger.begin("2");

        gpBasicLogger.end(begin2);
        gpBasicLogger.end(begin1);
    }

    @Test
    void begin_with_스레드() throws InterruptedException {
        int numOfExecute = 10;
        CountDownLatch latch = new CountDownLatch(numOfExecute);
        ExecutorService executorService = Executors.newFixedThreadPool(4);

        for(int i=0; i< numOfExecute; i++) {
            executorService.execute(() -> {
                GPBasicLog begin1 = gpBasicLogger.begin("1");
                GPBasicLog begin2 = gpBasicLogger.begin("2");
                GPBasicLog begin3 = gpBasicLogger.begin("3");

                gpBasicLogger.end(begin3);
                gpBasicLogger.end(begin2);
                gpBasicLogger.end(begin1);
                latch.countDown();
            });
        }
        latch.await();
    }

    @Test
    void begin_with_스레드_problem() throws InterruptedException {
        int numOfExecute = 10;
        CountDownLatch latch = new CountDownLatch(numOfExecute);
        ExecutorService executorService = Executors.newFixedThreadPool(4);

        for(int i=0; i< numOfExecute; i++) {
            executorService.execute(() -> {
                GPBasicLog begin1 = gpBasicLoggerProblem.begin("1");
                GPBasicLog begin2 = gpBasicLoggerProblem.begin("2");

                gpBasicLoggerProblem.end(begin2);
                gpBasicLoggerProblem.end(begin1);
                latch.countDown();
            });
        }
        latch.await();
    }

    @Test
    void end() {
    }

    @Test
    void exception() {
    }
}