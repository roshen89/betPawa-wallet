package com.betPawa.wallet.client.enums;

import com.betPawa.wallet.proto.BaseRequest;
import com.betPawa.wallet.proto.BaseResponse;
import com.betPawa.wallet.proto.Currency;
import com.betPawa.wallet.proto.WalletServiceGrpc.WalletServiceFutureStub;
import com.google.common.util.concurrent.ListenableFuture;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.springframework.core.task.TaskExecutor;

public enum Round {

  A {
    @Override
    public List<ListenableFuture<BaseResponse>> execute(final WalletServiceFutureStub futureStub,
        final Long userID, final TaskExecutor taskExecutor) {
      List<ListenableFuture<BaseResponse>> list = new ArrayList<>();

      list.add(Transaction.DEPOSIT.transact(
          futureStub, BaseRequest.newBuilder().setUserID(userID)
              .setAmount(BigDecimal.valueOf(100).toPlainString()).setCurrency(Currency.USD).build(),
          taskExecutor));

      list.add(Transaction.WITHDRAW.transact(futureStub, BaseRequest.newBuilder().setUserID(userID)
              .setAmount(BigDecimal.valueOf(200).toPlainString()).setCurrency(Currency.USD).build(),
          taskExecutor));

      list.add(Transaction.DEPOSIT.transact(
          futureStub, BaseRequest.newBuilder().setUserID(userID)
              .setAmount(BigDecimal.valueOf(100).toPlainString()).setCurrency(Currency.EUR).build(),
          taskExecutor));

      list.add(Transaction.BALANCE.transact(futureStub, BaseRequest.newBuilder().setUserID(userID).build(),
          taskExecutor));

      list.add(Transaction.WITHDRAW.transact(
          futureStub, BaseRequest.newBuilder().setUserID(userID)
              .setAmount(BigDecimal.valueOf(100).toPlainString()).setCurrency(Currency.USD).build(),
          taskExecutor));

      list.add(Transaction.BALANCE.transact(futureStub, BaseRequest.newBuilder().setUserID(userID).build(),
          taskExecutor));

      list.add(Transaction.WITHDRAW.transact(
          futureStub, BaseRequest.newBuilder().setUserID(userID)
              .setAmount(BigDecimal.valueOf(100).toPlainString()).setCurrency(Currency.USD).build(),
          taskExecutor));

      return list;
    }
  },

  B {
    @Override
    public List<ListenableFuture<BaseResponse>> execute(final WalletServiceFutureStub futureStub,
        final Long userID, final TaskExecutor taskExecutor) {
      List<ListenableFuture<BaseResponse>> list = new ArrayList<>();

      list.add(Transaction.WITHDRAW.transact(
          futureStub, BaseRequest.newBuilder().setUserID(userID)
              .setAmount(BigDecimal.valueOf(100).toPlainString()).setCurrency(Currency.GBP).build(),
          taskExecutor));

      list.add(Transaction.DEPOSIT.transact(futureStub,
          BaseRequest.newBuilder().setUserID(userID)
              .setAmount(BigDecimal.valueOf(300).toPlainString()).setCurrency(Currency.GBP)
              .build(),
          taskExecutor));

      list.add(Transaction.WITHDRAW.transact(
          futureStub, BaseRequest.newBuilder().setUserID(userID)
              .setAmount(BigDecimal.valueOf(100).toPlainString()).setCurrency(Currency.GBP).build(),
          taskExecutor));

      list.add(Transaction.WITHDRAW.transact(
          futureStub, BaseRequest.newBuilder().setUserID(userID)
              .setAmount(BigDecimal.valueOf(100).toPlainString()).setCurrency(Currency.GBP).build(),
          taskExecutor));

      list.add(Transaction.WITHDRAW.transact(
          futureStub, BaseRequest.newBuilder().setUserID(userID)
              .setAmount(BigDecimal.valueOf(100).toPlainString()).setCurrency(Currency.GBP).build(),
          taskExecutor));

      list.add(Transaction.WITHDRAW.transact(
          futureStub, BaseRequest.newBuilder().setUserID(userID)
              .setAmount(BigDecimal.valueOf(100).toPlainString()).setCurrency(Currency.GBP).build(),
          taskExecutor));

      list.add(Transaction.WITHDRAW.transact(
          futureStub, BaseRequest.newBuilder().setUserID(userID)
              .setAmount(BigDecimal.valueOf(100).toPlainString()).setCurrency(Currency.GBP).build(),
          taskExecutor));

      return list;
    }
  },

  C {
    @Override
    public List<ListenableFuture<BaseResponse>> execute(final WalletServiceFutureStub futureStub,
        final Long userID, final TaskExecutor taskExecutor) {
      List<ListenableFuture<BaseResponse>> list = new ArrayList<>();

      list.add(Transaction.BALANCE.transact(futureStub, BaseRequest.newBuilder().setUserID(userID).build(),
          taskExecutor));

      list.add(Transaction.DEPOSIT.transact(
          futureStub, BaseRequest.newBuilder().setUserID(userID)
              .setAmount(BigDecimal.valueOf(100).toPlainString()).setCurrency(Currency.USD).build(),
          taskExecutor));

      list.add(Transaction.DEPOSIT.transact(
          futureStub, BaseRequest.newBuilder().setUserID(userID)
              .setAmount(BigDecimal.valueOf(100).toPlainString()).setCurrency(Currency.USD).build(),
          taskExecutor));

      list.add(Transaction.WITHDRAW.transact(
          futureStub, BaseRequest.newBuilder().setUserID(userID)
              .setAmount(BigDecimal.valueOf(100).toPlainString()).setCurrency(Currency.USD).build(),
          taskExecutor));

      list.add(Transaction.DEPOSIT.transact(
          futureStub, BaseRequest.newBuilder().setUserID(userID)
              .setAmount(BigDecimal.valueOf(100).toPlainString()).setCurrency(Currency.USD).build(),
          taskExecutor));

      list.add(Transaction.BALANCE.transact(futureStub, BaseRequest.newBuilder().setUserID(userID).build(),
          taskExecutor));

      list.add(Transaction.WITHDRAW.transact(
          futureStub, BaseRequest.newBuilder().setUserID(userID)
              .setAmount(BigDecimal.valueOf(100).toPlainString()).setCurrency(Currency.USD).build(),
          taskExecutor));

      list.add(Transaction.BALANCE.transact(futureStub, BaseRequest.newBuilder().setUserID(userID).build(),
          taskExecutor));

      return list;
    }
  };

  public abstract List<ListenableFuture<BaseResponse>> execute(final WalletServiceFutureStub futureStub,
      final Long userID, final TaskExecutor taskExecutor);

}