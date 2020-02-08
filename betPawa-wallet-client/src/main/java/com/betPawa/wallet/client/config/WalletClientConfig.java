package com.betPawa.wallet.client.config;

import com.betPawa.wallet.proto.WalletServiceGrpc;
import com.betPawa.wallet.proto.WalletServiceGrpc.WalletServiceFutureStub;
import io.grpc.Channel;
import net.devh.springboot.autoconfigure.grpc.client.GrpcClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WalletClientConfig {

  @GrpcClient("betPawa-wallet-server")
  private Channel serverChannel;

  @Bean
  WalletServiceFutureStub getWalletServiceFutureStub() {
    return WalletServiceGrpc.newFutureStub(serverChannel);
  }
}
