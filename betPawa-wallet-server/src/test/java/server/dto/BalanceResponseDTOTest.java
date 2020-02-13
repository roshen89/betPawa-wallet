package server.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.betPawa.wallet.proto.Currency;
import com.betPawa.walllet.server.BetPawaWalletServerApplication;
import com.betPawa.walllet.server.dto.BalanceResponseDTO;
import com.betPawa.walllet.server.entity.Wallet;
import com.betPawa.walllet.server.entity.WalletPK;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = BetPawaWalletServerApplication.class)
@SpringBootTest
public class BalanceResponseDTOTest {

  @Autowired
  private BalanceResponseDTO dto;
  private List<Wallet> walletList = new ArrayList<>();

  @Before
  public void setUp() {
    Wallet wallet1 = Wallet.builder()
        .walletPK(WalletPK.builder().currency(Currency.EUR).userId(1L).build())
        .balance(BigDecimal.TEN)
        .build();
    Wallet wallet2 = Wallet.builder()
        .walletPK(WalletPK.builder().currency(Currency.USD).userId(1L).build())
        .balance(BigDecimal.TEN)
        .build();
    Wallet wallet3 = Wallet.builder()
        .walletPK(WalletPK.builder().currency(Currency.GBP).userId(1L).build())
        .balance(BigDecimal.ZERO)
        .build();

    walletList.add(wallet1);
    walletList.add(wallet2);
    walletList.add(wallet3);
  }

  @Test
  public void testGetBalanceAsString() {
    assertThat(dto.getBalanceAsString(walletList)).isNotEmpty();
  }

  @Test
  public void testGetBalanceResponseDTOFromString() {
    BalanceResponseDTO responseDTO = dto.getBalanceResponseDTOFromString(dto.getBalanceAsString(walletList));
    assertThat(responseDTO).isNotNull();
    assertThat(responseDTO.getBalance().size()).isEqualTo(3);
    assertThat(responseDTO.getBalance().get(Currency.GBP)).isEqualTo(BigDecimal.ZERO.toPlainString());
  }

}
