package com.betPawa.walllet.server.validation;

import com.betPawa.wallet.proto.Currency;
import com.betPawa.wallet.proto.StatusMessage;
import com.betPawa.walllet.server.exception.BetPawaException;
import io.grpc.Status;
import org.springframework.stereotype.Component;

@Component
public class BetPawaCurrencyValidator implements BetPawaValidator {

  public void checkCurrency(final Currency currency) {
    if (currency.equals(Currency.UNRECOGNIZED)) {
      throw new BetPawaException(Status.FAILED_PRECONDITION, StatusMessage.INVALID_CURRENCY);
    }
  }

}
