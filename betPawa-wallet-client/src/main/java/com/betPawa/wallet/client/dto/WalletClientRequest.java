package com.betPawa.wallet.client.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public final class WalletClientRequest {

  private Long countOfUsers;
  private Long countOfRequests;
  private Long countOfRounds;
}
