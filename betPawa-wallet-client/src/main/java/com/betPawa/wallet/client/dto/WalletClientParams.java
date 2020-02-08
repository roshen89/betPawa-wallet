package com.betPawa.wallet.client.dto;

import com.betPawa.wallet.proto.WalletServiceGrpc.WalletServiceFutureStub;
import java.util.concurrent.ExecutorService;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public final class WalletClientParams {

  private final Integer numberOfUsers;
  private final Integer numberOfRequests;
  private final Integer numberOfRounds;
  private final WalletServiceFutureStub futureStub;
  private final ExecutorService pool;

}
