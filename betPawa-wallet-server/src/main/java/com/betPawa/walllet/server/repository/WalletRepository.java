package com.betPawa.walllet.server.repository;

import com.betPawa.wallet.proto.CURRENCY;
import com.betPawa.walllet.server.entity.Wallet;
import com.betPawa.walllet.server.entity.WalletPK;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface WalletRepository extends JpaRepository<Wallet, WalletPK> {

  @Transactional(readOnly = true)
  Optional<List<Wallet>> findByWalletPK_UserID(Long userID);

  @Transactional(readOnly = true)
  @Query("select w from Wallet w where w.walletPK.userID =:userID and w.walletPK.currency=:currency")
  Optional<Wallet> getUserWalletsByCurrencyAndUserID(@Param("userID") Long userID, @Param("currency") CURRENCY currency);

  @Transactional(readOnly = true)
  @Query("select w from Wallet w where w.walletPK.userID =:userID and w.walletPK.currency=:currency and w.balance  > 0")
  Optional<Wallet> getUserWalletsByCurrencyAndUserIDWhereBalanceIsGreaterThanZero(@Param("userID") Long userID,
      @Param("currency") CURRENCY currency);

  @Transactional
  @Modifying(flushAutomatically = true, clearAutomatically = true)
  @Query("update Wallet wallet set wallet.balance =:newBalance where wallet.walletPK.userID =:userID and wallet.walletPK"
      + ".currency=:currency")
  void updateBalance(@Param("newBalance") BigDecimal newBalance, @Param("userID") Long userID, @Param("currency") CURRENCY currency);

}
