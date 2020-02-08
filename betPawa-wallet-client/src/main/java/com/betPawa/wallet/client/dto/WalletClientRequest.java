package com.betPawa.wallet.client.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public final class WalletClientRequest {

  private Long numberOfUsers;
  private Long numberOfRequests;
  private Long numberOfRounds;
}
