package com.betPawa.walllet.server.entity;

import com.betPawa.wallet.proto.Currency;
import java.io.Serializable;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class WalletPK implements Serializable {

  private static final long serialVersionUID = -8849946668166588603L;

  @NotNull
  @Size(max = 20)
  private Long userId;

  @NotNull
  @Enumerated(EnumType.STRING)
  private Currency currency;


  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((currency == null) ? 0 : currency.hashCode());
    result = prime * result + ((userId == null) ? 0 : userId.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    WalletPK other = (WalletPK) obj;
    if (currency != other.currency) {
      return false;
    }
    if (userId == null) {
      return other.userId == null;
    } else {
      return userId.equals(other.userId);
    }
  }

}