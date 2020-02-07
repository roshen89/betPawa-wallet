package com.betPawa.walllet.server.exception;

import com.betPawa.wallet.proto.StatusMessage;
import io.grpc.Metadata;
import io.grpc.Status;

public class BetPawaDataException extends BetPawaBaseException {

  private static final long serialVersionUID = 4890201354059182901L;

  public BetPawaDataException(Status status, Metadata trailers) {
    super(status, trailers);
  }

  public BetPawaDataException(Status status, StatusMessage errorStatus) {
    super(status, errorStatus);
  }

  public BetPawaDataException(Status status) {
    super(status);
  }

}
