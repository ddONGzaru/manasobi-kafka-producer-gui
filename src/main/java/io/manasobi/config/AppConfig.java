package io.manasobi.config;

import io.manasobi.domain.Point;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.core.task.support.TaskExecutorAdapter;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tw.jang on 2017-04-13.
 */
@Configuration
public class AppConfig {

    @Bean
    public List<Point> payloadList() {
        return new ArrayList<>();
    }

    @Bean
    public ThreadPoolTaskExecutor payloadTaskExecutor() {

        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();

        taskExecutor.setCorePoolSize(10);
        taskExecutor.setMaxPoolSize(10);
        taskExecutor.setQueueCapacity(30);

        return taskExecutor;
    }


}
