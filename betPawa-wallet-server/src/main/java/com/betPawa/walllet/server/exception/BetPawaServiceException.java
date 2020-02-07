package com.betPawa.walllet.server.exception;

import com.betPawa.wallet.proto.StatusMessage;
import io.grpc.Metadata;
import io.grpc.Status;

public class BetPawaServiceException extends BetPawaBaseException {

  private static final long serialVersionUID = 4890201354059182901L;

  public BetPawaServiceException(Status status, Metadata trailers) {
    super(status, trailers);
  }

  public BetPawaServiceException(Status status, StatusMessage errorStatus) {
    super(status, errorStatus);
  }

  public BetPawaServiceException(Status status) {
    super(status);
  }
}
