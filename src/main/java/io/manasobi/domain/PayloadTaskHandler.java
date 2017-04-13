package io.manasobi.domain;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by tw.jang on 2017-04-13.
 */
@Slf4j
@Component
public class PayloadTaskHandler {

    @Autowired
    private List<Point> payloadList;

    @Autowired
    private ThreadPoolTaskExecutor payloadTaskExecutor;

    @Autowired
    private PayloadWorker payloadWorker;

    public AtomicInteger count = new AtomicInteger();

    private List<Future<List<Point>>> futures = Lists.newArrayList();

    public void process(int payloadTotalSize) {

        for (int i = 0; i < 10; i++) {
            Future<List<Point>> future = payloadTaskExecutor.submit(() -> payloadWorker.work(payloadTotalSize / 10));
            futures.add(future);
        }

        futures.stream().forEach(this::findResult);

    }

    private void findResult(Future<List<Point>> future) {

        while (future.isDone()) {
            try {
                payloadList.addAll(future.get());
                count.addAndGet(future.get().size());
            } catch (Exception e) {
                log.error("PayloadTaskHandler-process :: {}", e.getMessage());
            }
        }
    }

}
