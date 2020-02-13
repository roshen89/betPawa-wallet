package com.betPawa.wallet.client.service.impl;

import com.betPawa.wallet.client.dto.WalletClientRequest;
import com.betPawa.wallet.client.service.ClientService;
import com.betPawa.wallet.client.supplier.UserSupplier;
import com.betPawa.wallet.proto.BaseResponse;
import com.betPawa.wallet.proto.Operation;
import com.betPawa.wallet.proto.Status;
import com.google.common.util.concurrent.ListenableFuture;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class WalletClientServiceImpl implements ClientService {

  private final UserSupplier userSupplier;

  public Map<Operation, Map<Status, AtomicLong>> run(final WalletClientRequest walletClientRequest) {

    final Map<Operation, Map<Status, AtomicLong>> operationStatusMap = new EnumMap<>(Operation.class);
    Arrays.stream(Operation.values()).forEach(op -> {
      Map<Status, AtomicLong> statusMap = new EnumMap<>(Status.class);
      Arrays.stream(Status.values()).forEach(val -> statusMap.put(val, new AtomicLong(0)));
      operationStatusMap.put(op, statusMap);
    });
    userSupplier.setWalletClientRequest(walletClientRequest);
    final List<ListenableFuture<BaseResponse>> roundsLFResponse = new ArrayList<>(userSupplier.get());
    roundsLFResponse.forEach(listenableFuture -> {
      try {
        BaseResponse response = listenableFuture.get();
        log.info(roundsLFResponse.size() + ":  " + listenableFuture.get().getStatus().name(), listenableFuture.get().getStatusMessage());
        operationStatusMap.get(response.getOperation()).get(Status.TRANSACTION_SUCCESS).incrementAndGet();
      } catch (Exception e) {
        log.error(e.getMessage());
        operationStatusMap.get(Operation.UNRECOGNIZED).get(Status.TRANSACTION_FAILED).incrementAndGet();
      }
    });
    log.info("operationStatusMap {}", operationStatusMap);
    return operationStatusMap;
  }

}
