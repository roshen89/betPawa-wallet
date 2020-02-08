package com.betPawa.walllet.server.exception;

import com.betPawa.wallet.proto.StatusMessage;
import io.grpc.Status;

public class BetPawaValidationException extends BetPawaBaseException {

  private static final long serialVersionUID = 4890201354059182901L;

  public BetPawaValidationException(Status status, StatusMessage errorStatus) {
    super(status, errorStatus);
  }


}
