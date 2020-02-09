package com.betPawa.wallet.client.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
public class ThreadPoolConfig {

  @Bean
  public TaskExecutor threadPoolTaskExecutor() {
    ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
    executor.setCorePoolSize(11);
    executor.setMaxPoolSize(11);
    executor.setQueueCapacity(500);
    executor.setThreadNamePrefix("betPawa-wallet-client");
    executor.initialize();
    return executor;
  }

}