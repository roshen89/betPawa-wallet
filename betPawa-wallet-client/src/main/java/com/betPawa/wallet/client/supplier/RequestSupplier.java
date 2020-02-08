package com.betPawa.wallet.client.supplier;

import com.betPawa.wallet.client.dto.WalletClientRequest;
import com.betPawa.wallet.proto.BaseResponse;
import com.google.common.util.concurrent.ListenableFuture;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
@Getter
@Setter
public class RequestSupplier implements Supplier<List<ListenableFuture<BaseResponse>>> {

  @Autowired
  private RoundSupplier roundSupplier;

  private WalletClientRequest walletClientRequest;
  private Long userID;

  @Override
  public List<ListenableFuture<BaseResponse>> get() {
    final List<ListenableFuture<BaseResponse>> round = new ArrayList<>();
    roundSupplier.setNumberOfRounds(walletClientRequest.getNumberOfRounds());
    roundSupplier.setUserID(userID);
    for (int i = 0; i < walletClientRequest.getNumberOfRequests(); i++) {
      round.addAll(roundSupplier.get());
    }
    return round;
  }
}
