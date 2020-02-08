package com.betPawa.wallet.client.dto;

import com.betPawa.wallet.proto.OPERATION;
import com.betPawa.wallet.proto.STATUS;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder(toBuilder = true)
@Getter
@AllArgsConstructor
public final class WalletClientResponse {

  private Map<OPERATION, Map<STATUS, AtomicLong>> transactions;
  private Long timeTaken;

}
