package com.betPawa.walllet.server.validation;

import com.betPawa.wallet.proto.StatusMessage;
import com.betPawa.walllet.server.entity.Wallet;
import com.betPawa.walllet.server.exception.BetPawaException;
import io.grpc.Status;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class BetPawaWalletValidator implements BetPawaValidator {

  public void validateWallet(Wallet wallet) {
    if (wallet == null) {
      throw new BetPawaException(Status.INTERNAL, StatusMessage.USER_DOES_NOT_EXIST);
    }
  }

  public void validateWallets(List<Wallet> wallets) {
    if (wallets == null) {
      throw new BetPawaException(Status.INTERNAL, StatusMessage.USER_DOES_NOT_EXIST);
    }
    if (wallets.isEmpty()) {
      throw new BetPawaException(Status.INTERNAL, StatusMessage.USER_DOES_NOT_EXIST);
    }
  }
}
