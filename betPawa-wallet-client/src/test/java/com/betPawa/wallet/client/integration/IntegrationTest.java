package com.betPawa.wallet.client.integration;

import static org.assertj.core.api.Assertions.assertThat;

import com.betPawa.wallet.client.BetPawaWalletClientApplication;
import com.betPawa.wallet.client.dto.BalanceResponseDTO;
import com.betPawa.wallet.client.enums.Transaction;
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

  private Long userID;

  @Before
  public void setUp() throws InterruptedException, ExecutionException {
    userID = 1L;
    resetBalanceToZeroForTestUser();
  }

  @Test
  public void walletTransactionTests() throws InterruptedException, ExecutionException {

    ListenableFuture<BaseResponse> futureResponse = withdraw(BigDecimal.valueOf(200), Currency.USD);
    try {
      System.out.println(futureResponse.get());
    } catch (ExecutionException e) {
      assertThat(e.getMessage()).contains(StatusMessage.INSUFFICIENT_BALANCE.name());
    }

    BaseResponse response = deposit(BigDecimal.valueOf(100), Currency.USD);
    assertThat(response.getStatus().name()).contains(Status.TRANSACTION_SUCCESS.name());

    BalanceResponseDTO balanceResponseDTO = getBalances();
    assertThat(new BigDecimal(balanceResponseDTO.getBalance().get(Currency.USD)).compareTo(BigDecimal.valueOf(100)) == 0).isTrue();

    futureResponse = withdraw(BigDecimal.valueOf(200), Currency.USD);
    try {
      System.out.println(futureResponse.get());
    } catch (ExecutionException e) {
      assertThat(e.getMessage()).contains(StatusMessage.INSUFFICIENT_BALANCE.name());
    }

    response = deposit(BigDecimal.valueOf(100), Currency.EUR);
    assertThat(response.getStatus().name()).contains(Status.TRANSACTION_SUCCESS.name());

    balanceResponseDTO = getBalances();
    assertThat(new BigDecimal(balanceResponseDTO.getBalance().get(Currency.USD)).compareTo(BigDecimal.valueOf(100)) == 0).isTrue();
    assertThat(new BigDecimal(balanceResponseDTO.getBalance().get(Currency.EUR)).compareTo(BigDecimal.valueOf(100)) == 0).isTrue();

    futureResponse = withdraw(BigDecimal.valueOf(200), Currency.USD);
    try {
      System.out.println(futureResponse.get());
    } catch (ExecutionException e) {
      assertThat(e.getMessage()).contains(StatusMessage.INSUFFICIENT_BALANCE.name());
    }

    response = deposit(BigDecimal.valueOf(100), Currency.USD);
    assertThat(response.getStatus().name()).contains(Status.TRANSACTION_SUCCESS.name());

    balanceResponseDTO = getBalances();
    assertThat(new BigDecimal(balanceResponseDTO.getBalance().get(Currency.USD)).compareTo(BigDecimal.valueOf(200)) == 0).isTrue();
    assertThat(new BigDecimal(balanceResponseDTO.getBalance().get(Currency.EUR)).compareTo(BigDecimal.valueOf(100)) == 0).isTrue();

    futureResponse = withdraw(BigDecimal.valueOf(200), Currency.USD);
    assertThat(futureResponse.get().getStatus().name()).contains(Status.TRANSACTION_SUCCESS.name());

    balanceResponseDTO = getBalances();
    assertThat(new BigDecimal(balanceResponseDTO.getBalance().get(Currency.USD)).compareTo(BigDecimal.valueOf(0)) == 0).isTrue();
    assertThat(new BigDecimal(balanceResponseDTO.getBalance().get(Currency.EUR)).compareTo(BigDecimal.valueOf(100)) == 0).isTrue();

    futureResponse = withdraw(BigDecimal.valueOf(200), Currency.USD);
    try {
      System.out.println(futureResponse.get());
    } catch (ExecutionException e) {
      assertThat(e.getMessage()).contains(StatusMessage.INSUFFICIENT_BALANCE.name());
    }
  }

  private ListenableFuture<BaseResponse> withdraw(BigDecimal amount, Currency currency) {
    return Transaction.WITHDRAW.doTransact(
        futureStub,
        BaseRequest.newBuilder()
            .setUserID(userID)
            .setAmount(amount.toPlainString())
            .setCurrency(currency)
            .build(),
        taskExecutor);
  }

  private BaseResponse deposit(BigDecimal amount, Currency currency) throws InterruptedException, ExecutionException {
    ListenableFuture<BaseResponse> futureResponse = Transaction.DEPOSIT.doTransact(
        futureStub,
        BaseRequest.newBuilder()
            .setUserID(userID)
            .setAmount(amount.toPlainString())
            .setCurrency(currency)
            .build(),
        taskExecutor);
    return futureResponse.get();
  }

  private BalanceResponseDTO getBalances() throws InterruptedException, ExecutionException {
    BaseResponse response = getBalanceForTestUser();
    return new Gson().fromJson(response.getStatusMessage(), BalanceResponseDTO.class);
  }

  private BaseResponse getBalanceForTestUser() throws InterruptedException, ExecutionException {
    ListenableFuture<BaseResponse> futureResponse;
    BaseResponse response;
    futureResponse = Transaction.BALANCE.doTransact(futureStub, BaseRequest.newBuilder().setUserID(userID).build(), taskExecutor);
    response = futureResponse.get();
    return response;
  }

  private void resetBalanceToZeroForTestUser() throws InterruptedException, ExecutionException {
    BaseResponse response = getBalanceForTestUser();
    BalanceResponseDTO balanceResponseDTO = new Gson().fromJson(response.getStatusMessage(), BalanceResponseDTO.class);
    withdrawAllFromTestUserWallet(balanceResponseDTO);
  }

  private void withdrawAllFromTestUserWallet(BalanceResponseDTO dto) {
    dto.getBalance().entrySet().stream().filter(es -> new BigDecimal(es.getValue()).compareTo(BigDecimal.ZERO) > 0).forEach(es -> {
      try {
        Transaction.WITHDRAW.doTransact(futureStub,
            BaseRequest.newBuilder().setUserID(userID).setAmount(es.getValue()).setCurrency(es.getKey()).build(),
            taskExecutor).get();
      } catch (InterruptedException | ExecutionException e) {
        e.printStackTrace();
      }
    });
  }

}
