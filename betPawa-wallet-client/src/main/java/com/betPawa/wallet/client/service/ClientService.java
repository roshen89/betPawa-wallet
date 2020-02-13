package com.betPawa.wallet.client.service;

import com.betPawa.wallet.client.dto.WalletClientRequest;
import com.betPawa.wallet.proto.Operation;
import com.betPawa.wallet.proto.Status;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Client Service will emulate a number of users concurrently using the wallet.
 */
public interface ClientService {

  /**
   * The method get walletClientRequest as input and after execute it stores response to Map<Operation, Map<Status, AtomicLong>>
   *
   * @return Map<OPERATION, Map < STATUS, AtomicLong>>
   */
  Map<Operation, Map<Status, AtomicLong>> run(final WalletClientRequest walletClientRequest);
}
