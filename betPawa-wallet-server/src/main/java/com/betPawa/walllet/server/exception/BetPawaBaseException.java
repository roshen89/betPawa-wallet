package com.betPawa.walllet.server.exception;

import com.betPawa.wallet.proto.StatusMessage;
import io.grpc.Metadata;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BetPawaBaseException extends StatusRuntimeException {

  private static final long serialVersionUID = 4267114442294762693L;
  private static final Logger logger = LoggerFactory.getLogger(BetPawaBaseException.class);

  private StatusMessage errorStatus;

  public BetPawaBaseException(Status status, Metadata trailers) {
    super(status, trailers);
  }

  public BetPawaBaseException(Status status) {
    super(status);
  }

  public BetPawaBaseException(Status status, StatusMessage errorStatus) {
    super(status);
    this.errorStatus = errorStatus;
  }

  public StatusMessage getErrorStatus() {
    return errorStatus;
  }

  public void setErrorStatus(StatusMessage errorStatus) {
    this.errorStatus = errorStatus;
  }
}
