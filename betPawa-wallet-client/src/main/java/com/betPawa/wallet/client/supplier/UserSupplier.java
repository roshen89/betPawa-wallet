package com.betPawa.wallet.client.supplier;

import com.betPawa.wallet.client.dto.WalletClientRequest;
import com.betPawa.wallet.proto.BaseResponse;
import com.google.common.util.concurrent.ListenableFuture;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class UserSupplier implements Supplier<List<ListenableFuture<BaseResponse>>> {

  @Autowired
  private RequestSupplier requestSupplier;

  private WalletClientRequest walletClientRequest;

  @Override
  public List<ListenableFuture<BaseResponse>> get() {
    final List<ListenableFuture<BaseResponse>> round = new ArrayList<>();
    requestSupplier.setWalletClientRequest(walletClientRequest);
    for (int userID = 1; userID <= walletClientRequest.getNumberOfUsers(); userID++) {
      requestSupplier.setUserID((long) userID);
      round.addAll(requestSupplier.get());
    }
    return round;
  }

  public void setWalletClientRequest(WalletClientRequest walletClientRequest) {
    this.walletClientRequest = walletClientRequest;
  }

}
