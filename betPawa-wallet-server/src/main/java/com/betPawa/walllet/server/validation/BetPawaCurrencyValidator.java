package com.betPawa.walllet.server.validation;

import com.betPawa.wallet.proto.CURRENCY;
import com.betPawa.wallet.proto.StatusMessage;
import com.betPawa.walllet.server.exception.BetPawaValidationException;
import io.grpc.Status;
import org.springframework.stereotype.Component;

@Component
public class BetPawaCurrencyValidator implements BetPawaBaseValidator<CURRENCY> {

  public void checkCurrency(final CURRENCY currency) {
    if (currency.equals(CURRENCY.UNRECOGNIZED)) {
      throw new BetPawaValidationException(Status.FAILED_PRECONDITION, StatusMessage.INVALID_CURRENCY);
    }
  }

}
