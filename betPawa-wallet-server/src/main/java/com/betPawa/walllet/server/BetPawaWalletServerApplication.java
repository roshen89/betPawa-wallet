package com.betPawa.walllet.server;

import com.betPawa.wallet.proto.CURRENCY;
import com.betPawa.walllet.server.entity.Wallet;
import com.betPawa.walllet.server.entity.WalletPK;
import com.betPawa.walllet.server.repository.WalletRepository;
import java.math.BigDecimal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
@EnableRetry
@EnableCaching
public class BetPawaWalletServerApplication {

  private static final Logger log = LoggerFactory.getLogger(BetPawaWalletServerApplication.class);

  @Autowired
  WalletRepository repository;

  public static void main(String[] args) {
    SpringApplication.run(BetPawaWalletServerApplication.class, args);
  }

  @Bean
  public CommandLineRunner buildData() {
    int numBerOfUsers = 100;
    return args -> {
      repository.deleteAll();
      for (long i = 1; i <= numBerOfUsers; i++) {
        for (int j = 0; j < CURRENCY.values().length - 1; j++) {
          repository.save(new Wallet(new WalletPK(i, CURRENCY.forNumber(j)), BigDecimal.ZERO));
        }
      }
      log.info("Initialized with {} users ", numBerOfUsers);
    };
  }

}
