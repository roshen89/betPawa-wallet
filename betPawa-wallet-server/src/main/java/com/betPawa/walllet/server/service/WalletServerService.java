package com.betPawa.walllet.server.service;

import com.betPawa.wallet.proto.BaseRequest;
import com.betPawa.wallet.proto.BaseResponse;
import com.betPawa.wallet.proto.OPERATION;
import com.betPawa.wallet.proto.STATUS;
import com.betPawa.wallet.proto.WalletServiceGrpc;
import com.betPawa.walllet.server.dto.BalanceResponseDTO;
import com.betPawa.walllet.server.entity.Wallet;
import com.betPawa.walllet.server.exception.BetPawaValidationException;
import com.betPawa.walllet.server.repository.WalletRepository;
import com.betPawa.walllet.server.validation.BetPawaAmountValidator;
import com.betPawa.walllet.server.validation.BetPawaCurrencyValidator;
import com.betPawa.walllet.server.validation.BetPawaWalletValidator;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import io.grpc.stub.StreamObserver;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.devh.springboot.autoconfigure.grpc.server.GrpcService;
import org.springframework.beans.factory.annotation.Autowired;

@GrpcService(WalletServiceGrpc.class)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class WalletServerService extends WalletServiceGrpc.WalletServiceImplBase {

  private final WalletRepository walletRepository;
  private final BetPawaAmountValidator bpAmountValidator;
  private final BetPawaCurrencyValidator bpCurrencyValidator;
  private final BetPawaWalletValidator bpWalletValidator;
  private final BalanceResponseDTO balanceResponseDTO;

  @Override
  public void deposit(final BaseRequest request, final StreamObserver<BaseResponse> responseObserver) {
    try {
      validateRequest(request);
      final BigDecimal balanceToADD = get(request.getAmount());
      log.info("Request Received for UserID:{} For Amount:{}{} ", request.getUserID(), request.getAmount(), request.getCurrency());
      Optional<Wallet> wallet = getUserWallet(request);
      wallet.ifPresent(bpWalletValidator::validateWallet);
      wallet.ifPresent(value -> updateWallet(value.getBalance().add(balanceToADD), value));
      successResponse(responseObserver, OPERATION.DEPOSIT);
      log.info("Wallet Updated SuccessFully");
    } catch (BetPawaValidationException e) {
      log.error(e.getErrorStatus().name());
      responseObserver.onError(new StatusRuntimeException(e.getStatus().withDescription(e.getErrorStatus().name())));
    } catch (Exception e) {
      log.error("------------>", e);
      responseObserver.onError(new StatusRuntimeException(Status.UNKNOWN.withDescription(e.getMessage())));
    } finally {
      walletRepository.flush();
    }
  }

  @Override
  public void withdraw(final BaseRequest request, final StreamObserver<BaseResponse> responseObserver) {
    log.info("Request Received for UserID:{} For Amount:{}{} ", request.getUserID(), request.getAmount(), request.getCurrency());
    try {
      final BigDecimal balanceToWithdraw = get(request.getAmount());
      validateRequest(request);
      Optional<Wallet> wallet = getUserWallet(request);
      wallet.ifPresent(value -> validateWithDrawRequest(balanceToWithdraw, value));
      wallet.ifPresent(value -> updateWallet(value.getBalance().subtract(balanceToWithdraw), value));
      successResponse(responseObserver, OPERATION.WITHDRAW);
    } catch (BetPawaValidationException e) {
      log.error(e.getErrorStatus().name());
      responseObserver.onError(new StatusRuntimeException(e.getStatus().withDescription(e.getErrorStatus().name())));
    } catch (Exception e) {
      log.error("------------>", e);
      responseObserver.onError(new StatusRuntimeException(Status.UNKNOWN.withDescription(e.getMessage())));
    } finally {
      walletRepository.flush();
    }
  }

  @Override
  public void balance(final BaseRequest request, final StreamObserver<BaseResponse> responseObserver) {
    log.info("Request Received for UserID:{}", request.getUserID());
    try {
      Optional<List<Wallet>> userWallets = walletRepository.findByWalletPK_UserID(request.getUserID());
      userWallets.ifPresent(bpWalletValidator::validate);
      String balance = null;
      if (userWallets.isPresent()) {
        balance = balanceResponseDTO.getBalanceAsString(userWallets.get());
      }
      log.info(balance);
      responseObserver.onNext(BaseResponse.newBuilder().setStatusMessage(balance)
          .setStatus((STATUS.TRANSACTION_SUCCESS)).setOperation(OPERATION.BALANCE).build());
      responseObserver.onCompleted();
    } catch (BetPawaValidationException e) {
      log.error(e.getErrorStatus().name());
      responseObserver.onError(new StatusRuntimeException(e.getStatus().withDescription(e.getErrorStatus().name())));
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      responseObserver.onError(new StatusRuntimeException(Status.UNKNOWN.withDescription(e.getMessage())));
    }

  }

  private BigDecimal get(final String val) {
    return new BigDecimal(val);
  }

  private void validateRequest(final BaseRequest request) {
    bpAmountValidator.validate(request.getAmount());
    bpCurrencyValidator.checkCurrency(request.getCurrency());
  }

  private void successResponse(final StreamObserver<BaseResponse> responseObserver, OPERATION operation) {
    responseObserver.onNext(
        BaseResponse.newBuilder().setStatus(STATUS.TRANSACTION_SUCCESS).setOperation(operation).build());
    responseObserver.onCompleted();
  }

  private Optional<Wallet> getUserWallet(final BaseRequest request) {
    return walletRepository.getUserWalletsByCurrencyAndUserID(request.getUserID(),
        request.getCurrency());
  }

  private void updateWallet(final BigDecimal newBalance, final Wallet wallet) {
    walletRepository.saveAndFlush(wallet.toBuilder().balance(newBalance).build());
  }

  private void validateWithDrawRequest(final BigDecimal balanceToWithdraw, Wallet wallet) {
    bpWalletValidator.validateWallet(wallet);
    bpAmountValidator.checkAmountLessThanBalance(wallet.getBalance(), balanceToWithdraw);
  }

}
