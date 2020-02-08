package com.betPawa.wallet.client.integration;

import static org.assertj.core.api.Assertions.assertThat;

import com.betPawa.wallet.client.BetPawaWalletClientApplication;
import com.betPawa.wallet.client.dto.BalanceResponseDTO;
import com.betPawa.wallet.client.enums.AMOUNT;
import com.betPawa.wallet.client.enums.TRANSACTION;
import com.betPawa.wallet.proto.BaseRequest;
import com.betPawa.wallet.proto.BaseResponse;
import com.betPawa.wallet.proto.CURRENCY;
import com.betPawa.wallet.proto.STATUS;
import com.betPawa.wallet.proto.StatusMessage;
import com.betPawa.wallet.proto.WalletServiceGrpc.WalletServiceFutureStub;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.gson.Gson;
import java.math.BigDecimal;
import java.util.concurrent.ExecutionException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.task.TaskExecutor;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = BetPawaWalletClientApplication.class)
@SpringBootTest
public class IntegrationTest {

  @Autowired
  private WalletServiceFutureStub futureStub;

  @Autowired
  private TaskExecutor taskExecutor;

  @Autowired
  private BalanceResponseDTO dto;

  private Long userID = 1L;

  @Before
  public void setUp() throws InterruptedException, ExecutionException {
    // This is Unnecessary- this is only a temporal fix to run com.betPawa.wallet.client.integration tests.
    // Mocking needs to be done
    resetBalanceToZeroForTestUser();
  }

  @Test
  public void tests() throws InterruptedException, ExecutionException {
    t1();
    t2();
    t3();
    t4();
    t5();
    t6();
    t7();
    t8();
    t9();
    t10();
    t11();
    t12();
  }

  private void t1() throws InterruptedException {
    ListenableFuture<BaseResponse> futureResponse;
    futureResponse = TRANSACTION.WITHDRAW.doTransact(
        futureStub, BaseRequest.newBuilder().setUserID(userID)
            .setAmount(AMOUNT.TWO_HUNDRED.getBigDecimalAmount().toPlainString()).setCurrency(CURRENCY.USD).build(),
        taskExecutor);
    try {
      System.out.println(futureResponse.get());
    } catch (ExecutionException e) {
      assertThat(e.getMessage()).contains(StatusMessage.INSUFFICIENT_BALANCE.name());
    }
  }

  private void t2() throws InterruptedException, ExecutionException {
    ListenableFuture<BaseResponse> futureResponse;
    BaseResponse response;
    futureResponse = TRANSACTION.DEPOSIT.doTransact(
        futureStub, BaseRequest.newBuilder().setUserID(userID)
            .setAmount(AMOUNT.HUNDRED.getBigDecimalAmount().toPlainString()).setCurrency(CURRENCY.USD).build(),
        taskExecutor);
    response = futureResponse.get();
    assertThat(response.getStatus().name()).contains(STATUS.TRANSACTION_SUCCESS.name());
  }

  private void t3() throws InterruptedException, ExecutionException {
    BaseResponse response = getBalanceForTestUser();
    BalanceResponseDTO balanceResponseDTO = new Gson().fromJson(response.getStatusMessage(), BalanceResponseDTO.class);

    assertThat(new BigDecimal(balanceResponseDTO.getBalance().get(CURRENCY.USD)).compareTo(AMOUNT.HUNDRED.getBigDecimalAmount()) == 0)
        .isTrue();

  }

  private void t4() throws InterruptedException {
    ListenableFuture<BaseResponse> futureResponse;
    futureResponse = TRANSACTION.WITHDRAW.doTransact(
        futureStub,
        BaseRequest.newBuilder()
            .setUserID(userID)
            .setAmount(AMOUNT.TWO_HUNDRED.getBigDecimalAmount().toPlainString())
            .setCurrency(CURRENCY.USD)
            .build(),
        taskExecutor);
    try {
      System.out.println(futureResponse.get());
    } catch (ExecutionException e) {
      assertThat(e.getMessage()).contains(StatusMessage.INSUFFICIENT_BALANCE.name());
    }
  }

  private void t5() throws InterruptedException, ExecutionException {
    ListenableFuture<BaseResponse> futureResponse;
    BaseResponse response;
    futureResponse = TRANSACTION.DEPOSIT.doTransact(
        futureStub, BaseRequest.newBuilder().setUserID(userID)
            .setAmount(AMOUNT.HUNDRED.getBigDecimalAmount().toPlainString()).setCurrency(CURRENCY.EUR).build(),
        taskExecutor);
    response = futureResponse.get();
    assertThat(response.getStatus().name()).contains(STATUS.TRANSACTION_SUCCESS.name());
  }

  private void t6() throws InterruptedException, ExecutionException {
    BaseResponse response = getBalanceForTestUser();

    BalanceResponseDTO balanceResponseDTO = new Gson().fromJson(response.getStatusMessage(), BalanceResponseDTO.class);

    assertThat(new BigDecimal(balanceResponseDTO.getBalance().get(CURRENCY.USD)).compareTo(AMOUNT.HUNDRED.getBigDecimalAmount()) == 0)
        .isTrue();
    assertThat(new BigDecimal(balanceResponseDTO.getBalance().get(CURRENCY.EUR)).compareTo(AMOUNT.HUNDRED.getBigDecimalAmount()) == 0)
        .isTrue();

  }

  private void t7() throws InterruptedException {
    ListenableFuture<BaseResponse> futureResponse;
    futureResponse = TRANSACTION.WITHDRAW.doTransact(
        futureStub, BaseRequest.newBuilder().setUserID(userID)
            .setAmount(AMOUNT.TWO_HUNDRED.getBigDecimalAmount().toPlainString()).setCurrency(CURRENCY.USD).build(),
        taskExecutor);
    try {
      System.out.println(futureResponse.get());
    } catch (ExecutionException e) {
      assertThat(e.getMessage()).contains(StatusMessage.INSUFFICIENT_BALANCE.name());
    }
  }

  private void t8() throws InterruptedException, ExecutionException {
    ListenableFuture<BaseResponse> futureResponse;
    BaseResponse response;
    futureResponse = TRANSACTION.DEPOSIT.doTransact(
        futureStub,
        BaseRequest.newBuilder()
            .setUserID(userID)
            .setAmount(AMOUNT.HUNDRED.getBigDecimalAmount().toPlainString())
            .setCurrency(CURRENCY.USD)
            .build(),
        taskExecutor);
    response = futureResponse.get();
    assertThat(response.getStatus().name()).contains(STATUS.TRANSACTION_SUCCESS.name());
  }

  private void resetBalanceToZeroForTestUser() throws InterruptedException, ExecutionException {
    BaseResponse response = getBalanceForTestUser();
    BalanceResponseDTO balanceResponseDTO = new Gson().fromJson(response.getStatusMessage(), BalanceResponseDTO.class);
    withdrawAllFromTestUserWallet(balanceResponseDTO);
  }

  private void t9() throws InterruptedException, ExecutionException {
    BaseResponse response = getBalanceForTestUser();
    BalanceResponseDTO balanceResponseDTO = new Gson().fromJson(response.getStatusMessage(), BalanceResponseDTO.class);

    assertThat(new BigDecimal(balanceResponseDTO.getBalance().get(CURRENCY.USD)).compareTo(AMOUNT.TWO_HUNDRED.getBigDecimalAmount()) == 0)
        .isTrue();
    assertThat(new BigDecimal(balanceResponseDTO.getBalance().get(CURRENCY.EUR)).compareTo(AMOUNT.HUNDRED.getBigDecimalAmount()) == 0)
        .isTrue();

  }

  private void t10() throws InterruptedException, ExecutionException {
    ListenableFuture<BaseResponse> futureResponse;
    BaseResponse response;
    futureResponse = TRANSACTION.WITHDRAW.doTransact(
        futureStub,
        BaseRequest.newBuilder()
            .setUserID(userID)
            .setAmount(AMOUNT.TWO_HUNDRED.getBigDecimalAmount().toPlainString())
            .setCurrency(CURRENCY.USD)
            .build(),
        taskExecutor);
    response = futureResponse.get();
    assertThat(response.getStatus().name()).contains(STATUS.TRANSACTION_SUCCESS.name());

  }

  private void t11() throws InterruptedException, ExecutionException {
    BaseResponse response = getBalanceForTestUser();
    BalanceResponseDTO balanceResponseDTO = new Gson().fromJson(response.getStatusMessage(), BalanceResponseDTO.class);

    assertThat(
        new BigDecimal(balanceResponseDTO.getBalance().get(CURRENCY.USD)).compareTo(AMOUNT.ZERO.getBigDecimalAmount()) == 0).isTrue();
    assertThat(new BigDecimal(balanceResponseDTO.getBalance().get(CURRENCY.EUR)).compareTo(AMOUNT.HUNDRED.getBigDecimalAmount()) == 0)
        .isTrue();

  }

  private void t12() throws InterruptedException {
    ListenableFuture<BaseResponse> futureResponse;
    futureResponse = TRANSACTION.WITHDRAW.doTransact(
        futureStub, BaseRequest.newBuilder().setUserID(userID)
            .setAmount(AMOUNT.TWO_HUNDRED.getBigDecimalAmount().toPlainString()).setCurrency(CURRENCY.USD).build(),
        taskExecutor);
    try {
      System.out.println(futureResponse.get());
    } catch (ExecutionException e) {
      assertThat(e.getMessage()).contains(StatusMessage.INSUFFICIENT_BALANCE.name());
    }
  }

  private void withdrawAllFromTestUserWallet(BalanceResponseDTO dto) {
    dto.getBalance().entrySet().stream().filter(es -> new BigDecimal(es.getValue()).compareTo(BigDecimal.ZERO) > 0

    ).forEach(es -> {
      try {
        TRANSACTION.WITHDRAW.doTransact(futureStub,
            BaseRequest.newBuilder().setUserID(userID).setAmount(es.getValue()).setCurrency(es.getKey()).build(),
            taskExecutor).get();
      } catch (InterruptedException | ExecutionException e) {
        e.printStackTrace();
      }

    });
  }

  private BaseResponse getBalanceForTestUser() throws InterruptedException, ExecutionException {
    ListenableFuture<BaseResponse> futureResponse;
    BaseResponse response;
    futureResponse = TRANSACTION.BALANCE.doTransact(futureStub, BaseRequest.newBuilder().setUserID(userID).build(), taskExecutor);
    response = futureResponse.get();
    return response;
  }

}
