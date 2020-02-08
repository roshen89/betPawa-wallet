package com.betPawa.wallet.client.enums;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AMOUNT {

  ZERO(BigDecimal.valueOf(0)),
  HUNDRED(BigDecimal.valueOf(100.00)),
  TWO_HUNDRED(BigDecimal.valueOf(200)),
  THREE_HUNDRED(BigDecimal.valueOf(300));

  private BigDecimal bigDecimalAmount;
}