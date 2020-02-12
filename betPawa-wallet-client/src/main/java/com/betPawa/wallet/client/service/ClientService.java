package com.betPawa.wallet.client.service;

import com.betPawa.wallet.client.dto.WalletClientRequest;
import com.betPawa.wallet.proto.Operation;
import com.betPawa.wallet.proto.Status;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;


public interface ClientService {

   /**
    *
    * @param walletClientRequest
    * @return Map<OPERATION, Map<STATUS, AtomicLong>>
    */
   Map<Operation, Map<Status, AtomicLong>> run(final WalletClientRequest walletClientRequest);
}
