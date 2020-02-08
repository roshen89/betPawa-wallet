package server.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.betPawa.wallet.proto.CURRENCY;
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
  public void setUp() throws Exception {
    Wallet wallet1 = (new Wallet(new WalletPK(1L, CURRENCY.EUR), (BigDecimal.TEN)));
    Wallet wallet2 = (new Wallet(new WalletPK(1L, CURRENCY.USD), (BigDecimal.TEN)));
    Wallet wallet3 = (new Wallet(new WalletPK(1L, CURRENCY.GBP), BigDecimal.ZERO));

    walletList.add(wallet1);
    walletList.add(wallet2);
    walletList.add(wallet3);
  }

  @Test
  public void testGetBalanceAsString() throws Exception {
    assertThat(dto.getBalanceAsString(walletList)).isNotEmpty();
  }

  @Test
  public void testGetBalanceResponseDTOFromString() throws Exception {
    BalanceResponseDTO responseDTO = dto.getBalanceResponseDTOFromString(dto.getBalanceAsString(walletList));
    assertThat(responseDTO).isNotNull();
    assertThat(responseDTO.getBalance().size()).isEqualTo(3);
    assertThat(responseDTO.getBalance().get(CURRENCY.GBP)).isEqualTo(BigDecimal.ZERO.toPlainString());
  }

}
