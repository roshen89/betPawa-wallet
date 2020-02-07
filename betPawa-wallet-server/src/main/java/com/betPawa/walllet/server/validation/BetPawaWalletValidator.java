package com.betPawa.walllet.server.validation;

import com.betPawa.wallet.proto.StatusMessage;
import com.betPawa.walllet.server.entity.Wallet;
import com.betPawa.walllet.server.exception.BetPawaValidationException;
import io.grpc.Status;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Component
public class BetPawaWalletValidator implements BetPawaBaseValidator<Optional<List<Wallet>>> {

  public void validateWallet(Optional<Wallet> t) {
    if (!t.isPresent()) {
      throw new BetPawaValidationException(Status.INTERNAL, StatusMessage.USER_DOES_NOT_EXIST);
    }

  }

  public void validate(Optional<List<Wallet>> t) {
    if (!t.isPresent()) {
      throw new BetPawaValidationException(Status.INTERNAL, StatusMessage.USER_DOES_NOT_EXIST);
    }

    if (CollectionUtils.isEmpty(t.get())) {
      throw new BetPawaValidationException(Status.INTERNAL, StatusMessage.USER_DOES_NOT_EXIST);
    }
  }
}
