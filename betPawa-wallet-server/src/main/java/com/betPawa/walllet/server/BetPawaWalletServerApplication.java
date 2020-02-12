package com.betPawa.walllet.server;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
@EnableRetry
@Slf4j
@EnableAutoConfiguration
public class BetPawaWalletServerApplication {

  public static void main(String[] args) {
    SpringApplication.run(BetPawaWalletServerApplication.class, args);
  }

}
