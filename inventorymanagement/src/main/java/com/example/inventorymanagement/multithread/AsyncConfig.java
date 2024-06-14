package com.example.inventorymanagement.multithread;

import java.util.concurrent.Executor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
@EnableAsync
public class AsyncConfig {
	@Bean(name = "taskExecutor")
    public Executor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(5); // minimum number of threads
        executor.setMaxPoolSize(10); // maximum number of threads
        executor.setQueueCapacity(100); // queue capacity before new threads are created
        executor.setThreadNamePrefix("Async-"); // prefix for thread names
        executor.initialize();
        return executor;
    }
}
