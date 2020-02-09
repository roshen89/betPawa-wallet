package com.betPawa.wallet.client.controller;

import com.betPawa.wallet.client.dto.WalletClientRequest;
import com.betPawa.wallet.client.dto.WalletClientResponse;
import com.betPawa.wallet.client.service.WalletClientService;
import java.util.concurrent.TimeUnit;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
@Slf4j
public class WalletClientController {

  @Autowired
  private WalletClientService service;

  @PostMapping
  public WalletClientResponse execute(@RequestBody WalletClientRequest clientRequest) {
    long time = System.currentTimeMillis();
    WalletClientResponse clientResponse = WalletClientResponse.builder().transactions(service.run(clientRequest)).build();
    long timeTaken = TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis() - time);
    log.info("Execution done time taken: {} {}", timeTaken, TimeUnit.SECONDS.name());
    return clientResponse.toBuilder().timeTaken(timeTaken).build();
  }
}
