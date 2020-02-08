package com.betPawa.wallet.client.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Data
@Builder(toBuilder = true)
public final class WalletClientRequest {

  private Long numberOfUsers;
  private Long numberOfRequests;
  private Long numberOfRounds;
}
