package com.betPawa.walllet.server.validation;

import com.betPawa.wallet.proto.StatusMessage;
import com.betPawa.walllet.server.exception.BetPawaException;
import io.grpc.Status;
import java.util.Objects;

public interface BetPawaBaseValidator<T> {

  default void validate(T t, String params) {
    if (Objects.isNull(t)) {
      throw new BetPawaException(Status.FAILED_PRECONDITION, StatusMessage.INVALID_ARGUMENTS);
    }
  }

}
