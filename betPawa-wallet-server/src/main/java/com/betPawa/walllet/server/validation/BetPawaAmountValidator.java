package com.betPawa.walllet.server.validation;

import com.betPawa.wallet.proto.StatusMessage;
import com.betPawa.walllet.server.exception.BetPawaException;
import io.grpc.Status;
import java.math.BigDecimal;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class BetPawaAmountValidator implements BetPawaBaseValidator<BigDecimal> {

  public void validate(final String amount) {
    if (StringUtils.isEmpty(amount)) {
      throw new BetPawaException(Status.FAILED_PRECONDITION, StatusMessage.INVALID_ARGUMENTS);

    }
    checkAmountFormat(amount);
    checkAmountGreaterThanZero(new BigDecimal(amount));
  }

  public void checkAmountFormat(final String amount) {
    try {
      new BigDecimal(amount);
    } catch (NumberFormatException numberFormatException) {
      throw new BetPawaException(Status.FAILED_PRECONDITION, StatusMessage.INVALID_ARGUMENTS);
    }
  }

  public void checkAmountGreaterThanZero(final BigDecimal amount) {
    if (amount.compareTo(BigDecimal.ZERO) < 1) {
      throw new BetPawaException(Status.FAILED_PRECONDITION,
          StatusMessage.AMOUNT_SHOULD_BE_GREATER_THAN_ZERO);
    }
  }

  public void checkAmountLessThanBalance(final BigDecimal currentBalance, final BigDecimal balanceToWithdraw) {
    if (currentBalance.compareTo(balanceToWithdraw) < 0) {
      throw new BetPawaException(Status.FAILED_PRECONDITION, StatusMessage.INSUFFICIENT_BALANCE);
    }
  }

}
