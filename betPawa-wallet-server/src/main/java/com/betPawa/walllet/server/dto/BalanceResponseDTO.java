package com.betPawa.walllet.server.dto;

import com.betPawa.wallet.proto.CURRENCY;
import com.betPawa.walllet.server.entity.Wallet;
import com.google.gson.Gson;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class BalanceResponseDTO {

  private Map<CURRENCY, String> balance;

  public Map<CURRENCY, String> getBalance() {
    if (balance == null) {
      balance = new EnumMap<>(CURRENCY.class);
      Arrays.stream(CURRENCY.values()).filter(checkInvalidCurrency()).forEach(currency -> balance.put(currency, "0"));
    }
    return balance;
  }

  public String getBalanceAsString(final List<Wallet> userWallets) {
    userWallets.stream().filter(checkInvalidCurrencyFromWallet()).forEach(wallet -> this
        .getBalance().put(wallet.getWalletPK().getCurrency(), wallet.getBalance().toPlainString()));
    return new Gson().toJson(this);
  }

  private Predicate<? super Wallet> checkInvalidCurrencyFromWallet() {
    return wallet -> wallet.getWalletPK().getCurrency() != CURRENCY.UNRECOGNIZED;
  }

  private Predicate<? super CURRENCY> checkInvalidCurrency() {
    return currency -> currency != CURRENCY.UNRECOGNIZED;
  }

}
