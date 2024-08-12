package com.kuit.healthmate.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Configuration
@EnableAsync
public class AsyncConfig implements AsyncConfigurer {

    @Override
    public Executor getAsyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(5); // 기본 스레드 풀 크기
        executor.setMaxPoolSize(10); // 최대 스레드 풀 크기
        executor.setQueueCapacity(500); // 큐 용량
        executor.setThreadNamePrefix("AsyncExecutor-"); // 스레드 이름 접두사
        executor.initialize();
        return executor;
    }
}