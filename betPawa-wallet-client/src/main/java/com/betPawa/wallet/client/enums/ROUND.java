package com.betPawa.wallet.client.enums;

import com.betPawa.wallet.proto.BaseRequest;
import com.betPawa.wallet.proto.BaseResponse;
import com.betPawa.wallet.proto.CURRENCY;
import com.betPawa.wallet.proto.WalletServiceGrpc.WalletServiceFutureStub;
import com.google.common.util.concurrent.ListenableFuture;
import java.util.ArrayList;
import java.util.List;
import org.springframework.core.task.TaskExecutor;

public enum ROUND {

  A {
    @Override
    public List<ListenableFuture<BaseResponse>> goExecute(final WalletServiceFutureStub futureStub,
        final Long userID, final TaskExecutor taskExecutor) {
      List<ListenableFuture<BaseResponse>> list = new ArrayList<>();

      list.add(TRANSACTION.DEPOSIT.doTransact(
          futureStub, BaseRequest.newBuilder().setUserID(userID)
              .setAmount(AMOUNT.HUNDRED.getBigDecimalAmount().toPlainString()).setCurrency(CURRENCY.USD).build(),
          taskExecutor));

      list.add(TRANSACTION.WITHDRAW.doTransact(futureStub, BaseRequest.newBuilder().setUserID(userID)
              .setAmount(AMOUNT.TWO_HUNDRED.getBigDecimalAmount().toPlainString()).setCurrency(CURRENCY.USD).build(),
          taskExecutor));

      list.add(TRANSACTION.DEPOSIT.doTransact(
          futureStub, BaseRequest.newBuilder().setUserID(userID)
              .setAmount(AMOUNT.HUNDRED.getBigDecimalAmount().toPlainString()).setCurrency(CURRENCY.EUR).build(),
          taskExecutor));

      list.add(TRANSACTION.BALANCE.doTransact(futureStub, BaseRequest.newBuilder().setUserID(userID).build(),
          taskExecutor));

      list.add(TRANSACTION.WITHDRAW.doTransact(
          futureStub, BaseRequest.newBuilder().setUserID(userID)
              .setAmount(AMOUNT.HUNDRED.getBigDecimalAmount().toPlainString()).setCurrency(CURRENCY.USD).build(),
          taskExecutor));

      list.add(TRANSACTION.BALANCE.doTransact(futureStub, BaseRequest.newBuilder().setUserID(userID).build(),
          taskExecutor));

      list.add(TRANSACTION.WITHDRAW.doTransact(
          futureStub, BaseRequest.newBuilder().setUserID(userID)
              .setAmount(AMOUNT.HUNDRED.getBigDecimalAmount().toPlainString()).setCurrency(CURRENCY.USD).build(),
          taskExecutor));

      return list;
    }

  },

  B {
    @Override
    public List<ListenableFuture<BaseResponse>> goExecute(final WalletServiceFutureStub futureStub,
        final Long userID, final TaskExecutor taskExecutor) {
      List<ListenableFuture<BaseResponse>> list = new ArrayList<>();

      list.add(TRANSACTION.WITHDRAW.doTransact(
          futureStub, BaseRequest.newBuilder().setUserID(userID)
              .setAmount(AMOUNT.HUNDRED.getBigDecimalAmount().toPlainString()).setCurrency(CURRENCY.GBP).build(),
          taskExecutor));

      list.add(TRANSACTION.DEPOSIT.doTransact(futureStub,
          BaseRequest.newBuilder().setUserID(userID)
              .setAmount(AMOUNT.THREE_HUNDRED.getBigDecimalAmount().toPlainString()).setCurrency(CURRENCY.GBP)
              .build(),
          taskExecutor));

      list.add(TRANSACTION.WITHDRAW.doTransact(
          futureStub, BaseRequest.newBuilder().setUserID(userID)
              .setAmount(AMOUNT.HUNDRED.getBigDecimalAmount().toPlainString()).setCurrency(CURRENCY.GBP).build(),
          taskExecutor));

      list.add(TRANSACTION.WITHDRAW.doTransact(
          futureStub, BaseRequest.newBuilder().setUserID(userID)
              .setAmount(AMOUNT.HUNDRED.getBigDecimalAmount().toPlainString()).setCurrency(CURRENCY.GBP).build(),
          taskExecutor));

      list.add(TRANSACTION.WITHDRAW.doTransact(
          futureStub, BaseRequest.newBuilder().setUserID(userID)
              .setAmount(AMOUNT.HUNDRED.getBigDecimalAmount().toPlainString()).setCurrency(CURRENCY.GBP).build(),
          taskExecutor));

      list.add(TRANSACTION.WITHDRAW.doTransact(
          futureStub, BaseRequest.newBuilder().setUserID(userID)
              .setAmount(AMOUNT.HUNDRED.getBigDecimalAmount().toPlainString()).setCurrency(CURRENCY.GBP).build(),
          taskExecutor));

      list.add(TRANSACTION.WITHDRAW.doTransact(
          futureStub, BaseRequest.newBuilder().setUserID(userID)
              .setAmount(AMOUNT.HUNDRED.getBigDecimalAmount().toPlainString()).setCurrency(CURRENCY.GBP).build(),
          taskExecutor));

      return list;
    }

  },
  C {
    @Override
    public List<ListenableFuture<BaseResponse>> goExecute(final WalletServiceFutureStub futureStub,
        final Long userID, final TaskExecutor taskExecutor) {
      List<ListenableFuture<BaseResponse>> list = new ArrayList<>();

      list.add(TRANSACTION.BALANCE.doTransact(futureStub, BaseRequest.newBuilder().setUserID(userID).build(),
          taskExecutor));

      list.add(TRANSACTION.DEPOSIT.doTransact(
          futureStub, BaseRequest.newBuilder().setUserID(userID)
              .setAmount(AMOUNT.HUNDRED.getBigDecimalAmount().toPlainString()).setCurrency(CURRENCY.USD).build(),
          taskExecutor));

      list.add(TRANSACTION.DEPOSIT.doTransact(
          futureStub, BaseRequest.newBuilder().setUserID(userID)
              .setAmount(AMOUNT.HUNDRED.getBigDecimalAmount().toPlainString()).setCurrency(CURRENCY.USD).build(),
          taskExecutor));

      list.add(TRANSACTION.WITHDRAW.doTransact(
          futureStub, BaseRequest.newBuilder().setUserID(userID)
              .setAmount(AMOUNT.HUNDRED.getBigDecimalAmount().toPlainString()).setCurrency(CURRENCY.USD).build(),
          taskExecutor));

      list.add(TRANSACTION.DEPOSIT.doTransact(
          futureStub, BaseRequest.newBuilder().setUserID(userID)
              .setAmount(AMOUNT.HUNDRED.getBigDecimalAmount().toPlainString()).setCurrency(CURRENCY.USD).build(),
          taskExecutor));

      list.add(TRANSACTION.BALANCE.doTransact(futureStub, BaseRequest.newBuilder().setUserID(userID).build(),
          taskExecutor));

      list.add(TRANSACTION.WITHDRAW.doTransact(
          futureStub, BaseRequest.newBuilder().setUserID(userID)
              .setAmount(AMOUNT.HUNDRED.getBigDecimalAmount().toPlainString()).setCurrency(CURRENCY.USD).build(),
          taskExecutor));

      list.add(TRANSACTION.BALANCE.doTransact(futureStub, BaseRequest.newBuilder().setUserID(userID).build(),
          taskExecutor));

      return list;
    }
  };

  public abstract List<ListenableFuture<BaseResponse>> goExecute(final WalletServiceFutureStub futureStub,
      final Long userID, final TaskExecutor taskExecutor);

}