package com.betPawa.wallet.client.service;

import com.betPawa.wallet.client.dto.WalletClientRequest;
import com.betPawa.wallet.client.supplier.UserSupplier;
import com.betPawa.wallet.proto.BaseResponse;
import com.betPawa.wallet.proto.OPERATION;
import com.betPawa.wallet.proto.STATUS;
import com.google.common.util.concurrent.ListenableFuture;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WalletClientService {

  private static final Logger logger = LoggerFactory.getLogger(WalletClientService.class);

  @Autowired
  private UserSupplier userSupplier;

  public Map<OPERATION, Map<STATUS, AtomicLong>> run(final WalletClientRequest walletClientRequest) {

    final Map<OPERATION, Map<STATUS, AtomicLong>> operationStatusMap = new EnumMap<>(OPERATION.class);

    Arrays.stream(OPERATION.values()).forEach(op -> {
      Map<STATUS, AtomicLong> statusMap = new EnumMap<>(STATUS.class);
      Arrays.stream(STATUS.values()).forEach(val -> statusMap.put(val, new AtomicLong(0)));
      operationStatusMap.put(op, statusMap);
    });

    userSupplier.setWalletClientRequest(walletClientRequest);
    final List<ListenableFuture<BaseResponse>> roundsLFResponse = new ArrayList<>(userSupplier.get());
    roundsLFResponse.forEach(listenableFuture -> {
      try {
        BaseResponse response = listenableFuture.get();
        logger.info(roundsLFResponse.size() + ":  " + listenableFuture.get().getStatus().name(), listenableFuture.get().getStatusMessage());
        operationStatusMap.get(response.getOperation()).get(STATUS.TRANSACTION_SUCCESS).incrementAndGet();
      } catch (Exception e) {
        logger.error(e.getMessage());
        operationStatusMap.get(OPERATION.UNRECOGNIZED).get(STATUS.TRANSACTION_FAILED).incrementAndGet();
      }
    });
    logger.info("operationStatusMap {}", operationStatusMap);
    return operationStatusMap;
  }

}
