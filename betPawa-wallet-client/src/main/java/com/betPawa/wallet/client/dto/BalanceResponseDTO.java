package com.betPawa.wallet.client.dto;

import com.betPawa.wallet.proto.Currency;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.Map;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class BalanceResponseDTO {

  private Map<Currency, String> balance;

  public Map<Currency, String> getBalance() {
    if (balance == null) {
      balance = new EnumMap<>(Currency.class);
      Arrays.stream(Currency.values()).forEach(currency -> balance.put(currency, "0"));
    }
    return balance;
  }

}
