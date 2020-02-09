package com.betPawa.wallet.client.enums;

import static com.google.common.util.concurrent.Futures.addCallback;

import com.betPawa.wallet.proto.BaseRequest;
import com.betPawa.wallet.proto.BaseResponse;
import com.betPawa.wallet.proto.OPERATION;
import com.betPawa.wallet.proto.WalletServiceGrpc.WalletServiceFutureStub;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.ListenableFuture;
import io.grpc.Status;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.task.TaskExecutor;
import org.springframework.lang.NonNull;

@Slf4j
public enum TRANSACTION {

  DEPOSIT {
    @Override
    public ListenableFuture<BaseResponse> doTransact(final WalletServiceFutureStub futureStub, final BaseRequest baseRequest,
        final TaskExecutor taskExecutor) {
      ListenableFuture<BaseResponse> response = futureStub.deposit(baseRequest);
      addCallback(response, new FutureCallback<BaseResponse>() {

        @Override
        public void onSuccess(BaseResponse result) {
          result.toBuilder().setOperation(OPERATION.DEPOSIT);
          log.info("{} {}", result.getStatus().name(), result.getStatusMessage());
        }

        @Override
        public void onFailure(@NonNull Throwable throwable) {
          log.warn(Status.fromThrowable(throwable).getDescription());
        }
      }, taskExecutor);

      return response;
    }
  },
  WITHDRAW {
    @Override
    public ListenableFuture<BaseResponse> doTransact(final WalletServiceFutureStub futureStub,
        final BaseRequest baseRequest, final TaskExecutor taskExecutor) {
      ListenableFuture<BaseResponse> response = futureStub.withdraw(baseRequest);

      addCallback(response, new FutureCallback<BaseResponse>() {
        @Override
        public void onSuccess(BaseResponse result) {
          result.toBuilder().setOperation(OPERATION.WITHDRAW);
          log.info("{} {}", result.getStatus().name(), result.getStatusMessage());
        }

        @Override
        public void onFailure(@NonNull Throwable throwable) {
          log.warn(Status.fromThrowable(throwable).getDescription());
        }
      }, taskExecutor);
      return response;

    }
  },
  BALANCE {
    @Override
    public ListenableFuture<BaseResponse> doTransact(final WalletServiceFutureStub futureStub,
        final BaseRequest baseRequest, final TaskExecutor taskExecutor) {
      ListenableFuture<BaseResponse> response = futureStub.balance(baseRequest);

      addCallback(response, new FutureCallback<BaseResponse>() {
        @Override
        public void onSuccess(BaseResponse result) {
          result.toBuilder().setOperation(OPERATION.BALANCE);
          log.info("{} {}", result.getStatus().name(), result.getStatusMessage());
        }

        @Override
        public void onFailure(@NonNull Throwable throwable) {
          log.warn(Status.fromThrowable(throwable).getDescription());
        }
      }, taskExecutor);
      return response;
    }
  };

  public abstract ListenableFuture<BaseResponse> doTransact(final WalletServiceFutureStub futureStub,
      final BaseRequest baseRequest, final TaskExecutor taskExecutor);
}