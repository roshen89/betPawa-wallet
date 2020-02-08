package com.betPawa.wallet.client.supplier;

import com.betPawa.wallet.client.enums.ROUND;
import com.betPawa.wallet.proto.BaseResponse;
import com.betPawa.wallet.proto.WalletServiceGrpc.WalletServiceFutureStub;
import com.google.common.util.concurrent.ListenableFuture;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Supplier;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
@Getter
@Setter
public class RoundSupplier implements Supplier<List<ListenableFuture<BaseResponse>>> {

  @Autowired
  private WalletServiceFutureStub walletServiceFutureStub;

  @Autowired
  private TaskExecutor taskExecutor;

  private Long numberOfRounds;
  private String stats;
  private Long userID;

  @Override
  public List<ListenableFuture<BaseResponse>> get() {
    final List<ListenableFuture<BaseResponse>> round = new ArrayList<>();
    for (int i = 0; i < numberOfRounds; i++) {
      round.addAll(ROUND.values()[ThreadLocalRandom.current().nextInt(0, (ROUND.values().length))]
          .goExecute(walletServiceFutureStub, userID, taskExecutor));
    }
    return round;
  }


}
