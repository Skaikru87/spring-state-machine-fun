package com.mkm.springstatemachinefun.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@Slf4j
@Component

public class TaskExecutotBean {
    @Bean
    @Qualifier("taskExecutorBean")
    public Executor getTaskExecutor() {
        log.info("Setting thread pool to 100");
        return Executors.newScheduledThreadPool(100);
    }
}
