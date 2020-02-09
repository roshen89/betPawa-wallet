package com.betPawa.wallet.client.controller;

import com.betPawa.wallet.client.dto.WalletClientRequest;
import com.betPawa.wallet.client.dto.WalletClientResponse;
import com.betPawa.wallet.client.service.WalletClientService;
import java.util.concurrent.TimeUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class WalletClientController {

  private static final Logger logger = LoggerFactory.getLogger(WalletClientController.class);

  @Autowired
  private WalletClientService walletClientService;

  @PostMapping
  public WalletClientResponse execute(@RequestBody WalletClientRequest walletClientRequest) {
    WalletClientResponse response = WalletClientResponse.builder().transactions(walletClientService.run(walletClientRequest)).build();
    long time = System.currentTimeMillis();
    long timeTaken = TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis() - time);
    logger.info("Execution done time taken: {} {}", timeTaken, TimeUnit.SECONDS.name());
    return response.toBuilder().timeTaken(timeTaken).build();
  }
}
