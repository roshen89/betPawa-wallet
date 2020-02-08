package com.betPawa.wallet.client.enums;

import java.math.BigDecimal;

public enum AMOUNT {

  HUNDRED(BigDecimal.valueOf(100.00)),
  TWO_HUNDRED(BigDecimal.valueOf(200)),
  THREE_HUNDRED(BigDecimal.valueOf(300));

  private BigDecimal bigDecimalAmount;

  public BigDecimal getBigDecimalAmount() {
    return bigDecimalAmount;
  }

  AMOUNT(BigDecimal bigDecimalAmount) {
    this.bigDecimalAmount = bigDecimalAmount;
  }

}