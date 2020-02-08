package com.betPawa.walllet.server.validation;

import com.betPawa.wallet.proto.StatusMessage;
import com.betPawa.walllet.server.entity.Wallet;
import com.betPawa.walllet.server.exception.BetPawaValidationException;
import io.grpc.Status;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Component;

@Component
public class BetPawaWalletValidator implements BetPawaBaseValidator<Optional<List<Wallet>>> {

  public void validateWallet(Wallet wallet) {
    if (wallet == null) {
      throw new BetPawaValidationException(Status.INTERNAL, StatusMessage.USER_DOES_NOT_EXIST);
    }
  }

  public void validate(List<Wallet> wallets) {
    if (wallets == null) {
      throw new BetPawaValidationException(Status.INTERNAL, StatusMessage.USER_DOES_NOT_EXIST);
    }

    if (wallets.isEmpty()) {
      throw new BetPawaValidationException(Status.INTERNAL, StatusMessage.USER_DOES_NOT_EXIST);
    }
  }
}
