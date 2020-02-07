package com.betPawa.walllet.server.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "wallet")
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Wallet implements Serializable {

  private static final long serialVersionUID = -9189082357635718615L;

  @EmbeddedId
  private WalletPK walletPK;
  @NotNull
  private BigDecimal balance;
  @Version
  private Integer version;

  public Wallet(WalletPK walletPK, @NotNull BigDecimal balance) {
    super();
    this.walletPK = walletPK;
    this.balance = balance;
  }
}
