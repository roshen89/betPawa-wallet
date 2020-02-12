package com.betPawa.wallet.client.dto;

import com.betPawa.wallet.proto.Operation;
import com.betPawa.wallet.proto.Status;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder(toBuilder = true)
@Getter
@AllArgsConstructor
public final class WalletClientResponse {

  private Map<Operation, Map<Status, AtomicLong>> transactions;
  private Long timeTaken;

}
