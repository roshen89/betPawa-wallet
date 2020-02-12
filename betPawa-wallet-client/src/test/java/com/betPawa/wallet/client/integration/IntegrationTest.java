package com.betPawa.wallet.client.integration;

import static org.assertj.core.api.Assertions.assertThat;

import com.betPawa.wallet.client.BetPawaWalletClientApplication;
import com.betPawa.wallet.client.dto.BalanceResponseDTO;
import com.betPawa.wallet.client.enums.TRANSACTION;
import com.betPawa.wallet.proto.BaseRequest;
import com.betPawa.wallet.proto.BaseResponse;
import com.betPawa.wallet.proto.Currency;
import com.betPawa.wallet.proto.Status;
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
    resetBalanceToZeroForTestUser();
  }

  @Test
  public void walletTransactionTests() throws InterruptedException, ExecutionException {
    test_withdraw_TwoHundred_USD(BigDecimal.valueOf(200), Currency.USD);
    test_deposit_HUNDERD_USD();
    test_checkUSDBalance();
    test_withdraw_TwoHundred_USD(BigDecimal.valueOf(200), Currency.USD);
    test_deposit_HUNDERD_EUR();
    test_checkAllBalances();
    test_withdraw_TwoHundred_USD(BigDecimal.valueOf(200), Currency.USD);
    test_deposit_HUNDERD_USD();
    test9();
    test_withdraw_TwoHundred_USD_SUCCESS();
    test11();
    test_withdraw_TwoHundred_USD(BigDecimal.valueOf(200), Currency.USD);
  }

  private void test_withdraw_TwoHundred_USD(BigDecimal amount, Currency currency) throws InterruptedException {
    ListenableFuture<BaseResponse> futureResponse;
    futureResponse = TRANSACTION.WITHDRAW.doTransact(
        futureStub,
        BaseRequest.newBuilder()
            .setUserID(userID)
            .setAmount(amount.toPlainString())
            .setCurrency(currency)
            .build(),
        taskExecutor);
    try {
      System.out.println(futureResponse.get());
    } catch (ExecutionException e) {
      assertThat(e.getMessage()).contains(StatusMessage.INSUFFICIENT_BALANCE.name());
    }
  }

  private void test_deposit_HUNDERD_USD() throws InterruptedException, ExecutionException {
    ListenableFuture<BaseResponse> futureResponse;
    BaseResponse response;
    futureResponse = TRANSACTION.DEPOSIT.doTransact(
        futureStub,
        BaseRequest.newBuilder()
            .setUserID(userID)
            .setAmount(BigDecimal.valueOf(100).toPlainString())
            .setCurrency(Currency.USD)
            .build(),
        taskExecutor);
    response = futureResponse.get();
    assertThat(response.getStatus().name()).contains(Status.TRANSACTION_SUCCESS.name());
  }

  private void test_checkUSDBalance() throws InterruptedException, ExecutionException {
    BaseResponse response = getBalanceForTestUser();
    BalanceResponseDTO balanceResponseDTO = new Gson().fromJson(response.getStatusMessage(), BalanceResponseDTO.class);

    assertThat(new BigDecimal(balanceResponseDTO.getBalance().get(Currency.USD)).compareTo(BigDecimal.valueOf(100)) == 0)
        .isTrue();
  }

  private void test_deposit_HUNDERD_EUR() throws InterruptedException, ExecutionException {
    ListenableFuture<BaseResponse> futureResponse;
    BaseResponse response;
    futureResponse = TRANSACTION.DEPOSIT.doTransact(
        futureStub,
        BaseRequest.newBuilder()
            .setUserID(userID)
            .setAmount(BigDecimal.valueOf(100).toPlainString())
            .setCurrency(Currency.EUR)
            .build(),
        taskExecutor);
    response = futureResponse.get();
    assertThat(response.getStatus().name()).contains(Status.TRANSACTION_SUCCESS.name());
  }

  private void test_checkAllBalances() throws InterruptedException, ExecutionException {
    BaseResponse response = getBalanceForTestUser();

    BalanceResponseDTO balanceResponseDTO = new Gson().fromJson(response.getStatusMessage(), BalanceResponseDTO.class);

    assertThat(new BigDecimal(balanceResponseDTO.getBalance().get(Currency.USD)).compareTo(BigDecimal.valueOf(100)) == 0)
        .isTrue();
    assertThat(new BigDecimal(balanceResponseDTO.getBalance().get(Currency.EUR)).compareTo(BigDecimal.valueOf(100)) == 0)
        .isTrue();

  }

  private void test9() throws InterruptedException, ExecutionException {
    BaseResponse response = getBalanceForTestUser();
    BalanceResponseDTO balanceResponseDTO = new Gson().fromJson(response.getStatusMessage(), BalanceResponseDTO.class);

    assertThat(new BigDecimal(balanceResponseDTO.getBalance().get(Currency.USD)).compareTo(BigDecimal.valueOf(200)) == 0)
        .isTrue();
    assertThat(new BigDecimal(balanceResponseDTO.getBalance().get(Currency.EUR)).compareTo(BigDecimal.valueOf(100)) == 0)
        .isTrue();

  }

  private void test_withdraw_TwoHundred_USD_SUCCESS() throws InterruptedException, ExecutionException {
    ListenableFuture<BaseResponse> futureResponse;
    BaseResponse response;
    futureResponse = TRANSACTION.WITHDRAW.doTransact(
        futureStub,
        BaseRequest.newBuilder()
            .setUserID(userID)
            .setAmount(BigDecimal.valueOf(200).toPlainString())
            .setCurrency(Currency.USD)
            .build(),
        taskExecutor);
    response = futureResponse.get();
    assertThat(response.getStatus().name()).contains(Status.TRANSACTION_SUCCESS.name());

  }

  private void test11() throws InterruptedException, ExecutionException {
    BaseResponse response = getBalanceForTestUser();
    BalanceResponseDTO balanceResponseDTO = new Gson().fromJson(response.getStatusMessage(), BalanceResponseDTO.class);
    assertThat(
        new BigDecimal(balanceResponseDTO.getBalance().get(Currency.USD)).compareTo(BigDecimal.valueOf(0)) == 0).isTrue();
    assertThat(
        new BigDecimal(balanceResponseDTO.getBalance().get(Currency.EUR)).compareTo(BigDecimal.valueOf(100)) == 0).isTrue();

  }

  private BaseResponse getBalanceForTestUser() throws InterruptedException, ExecutionException {
    ListenableFuture<BaseResponse> futureResponse;
    BaseResponse response;
    futureResponse = TRANSACTION.BALANCE.doTransact(futureStub, BaseRequest.newBuilder().setUserID(userID).build(), taskExecutor);
    response = futureResponse.get();
    return response;
  }

  private void resetBalanceToZeroForTestUser() throws InterruptedException, ExecutionException {
    BaseResponse response = getBalanceForTestUser();
    BalanceResponseDTO balanceResponseDTO = new Gson().fromJson(response.getStatusMessage(), BalanceResponseDTO.class);
    withdrawAllFromTestUserWallet(balanceResponseDTO);
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

}
