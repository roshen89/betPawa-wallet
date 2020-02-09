package com.betPawa.walllet.server.exception;

import com.betPawa.wallet.proto.StatusMessage;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import lombok.Getter;

@Getter
public class BetPawaException extends StatusRuntimeException {

  private static final long serialVersionUID = 4267114442294762693L;

  private final StatusMessage errorStatus;

  public BetPawaException(Status status, StatusMessage errorStatus) {
    super(status);
    this.errorStatus = errorStatus;
  }
}
