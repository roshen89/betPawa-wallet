package com.betPawa.walllet.server.entity;

import com.betPawa.wallet.proto.CURRENCY;
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
  private Long userID;

  @NotNull
  @Enumerated(EnumType.STRING)
  private CURRENCY currency;


  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((currency == null) ? 0 : currency.hashCode());
    result = prime * result + ((userID == null) ? 0 : userID.hashCode());
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
    if (userID == null) {
      return other.userID == null;
    } else {
      return userID.equals(other.userID);
    }
  }

}