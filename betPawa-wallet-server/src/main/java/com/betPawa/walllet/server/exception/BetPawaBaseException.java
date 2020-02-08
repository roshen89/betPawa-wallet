package com.betPawa.walllet.server.exception;

import com.betPawa.wallet.proto.StatusMessage;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;

public class BetPawaBaseException extends StatusRuntimeException {

  private static final long serialVersionUID = 4267114442294762693L;

  private StatusMessage errorStatus;

  public BetPawaBaseException(Status status, StatusMessage errorStatus) {
    super(status);
    this.errorStatus = errorStatus;
  }

  public StatusMessage getErrorStatus() {
    return errorStatus;
  }
}
