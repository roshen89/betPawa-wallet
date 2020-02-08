package com.betPawa.wallet.client.dto;

import com.betPawa.wallet.proto.CURRENCY;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.Map;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class BalanceResponseDTO {

  private Map<CURRENCY, String> balance;

  public Map<CURRENCY, String> getBalance() {
    if (balance == null) {
      balance = new EnumMap<>(CURRENCY.class);
      Arrays.stream(CURRENCY.values()).forEach(currency -> balance.put(currency, "0"));
    }
    return balance;
  }

}
