package com.betPawa.wallet.client;

import com.betPawa.wallet.client.dto.WalletClientRequest;
import com.betPawa.wallet.client.service.WalletClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class BetPawaWalletClientApplication {

  @Autowired
  WalletClientService walletClientService;

  public static void main(String[] args) {
    SpringApplication.run(BetPawaWalletClientApplication.class, args);
  }

  @Bean
  public CommandLineRunner execute() {
    WalletClientRequest walletClientRequest = WalletClientRequest.builder()
        .numberOfRequests(1L)
        .numberOfRounds(1L)
        .numberOfUsers(10L)
        .build();
    return args -> walletClientService.run(walletClientRequest);
  }

}
