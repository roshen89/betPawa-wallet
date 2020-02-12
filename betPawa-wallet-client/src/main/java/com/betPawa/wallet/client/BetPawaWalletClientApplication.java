package com.betPawa.wallet.client;

import com.betPawa.wallet.client.service.impl.WalletClientServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BetPawaWalletClientApplication {

  @Autowired
  WalletClientServiceImpl walletClientServiceImpl;

  public static void main(String[] args) {
    SpringApplication.run(BetPawaWalletClientApplication.class, args);
  }

}
