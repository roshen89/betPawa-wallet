package com.betPawa.wallet.client.service;

import com.betPawa.wallet.client.dto.WalletClientRequest;
import com.betPawa.wallet.proto.OPERATION;
import com.betPawa.wallet.proto.STATUS;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;


public interface ClientService {

   /**
    *
    * @param walletClientRequest
    * @return Map<OPERATION, Map<STATUS, AtomicLong>>
    */
   Map<OPERATION, Map<STATUS, AtomicLong>> run(final WalletClientRequest walletClientRequest);
}
