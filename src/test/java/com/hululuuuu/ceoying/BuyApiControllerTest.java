package com.hululuuuu.ceoying;

import com.hululuuuu.ceoying.domain.wallet.Wallet;
import com.hululuuuu.ceoying.domain.wallet.WalletRepository;
import com.hululuuuu.ceoying.domain.yiying.Buy;
import com.hululuuuu.ceoying.domain.yiying.BuyRepository;
import com.hululuuuu.ceoying.web.dto.buy.BuySaveRequestDto;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BuyApiControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private BuyRepository buyRepository;

    @Autowired
    private WalletRepository walletRepository;

    @Before
    public void saveMoney() throws Exception {
        walletRepository.save(Wallet.builder()
        .money(1000)
        .record("+1000")
        .statement("입금")
        .statementDate(LocalDate.now())
        .build());
    }

    @After
    public void tearDown() throws Exception {
        buyRepository.deleteAll();
        walletRepository.deleteAll();
    }

    @Test
    public void buy_등록된다() throws Exception{
        //given
        String name = "김씨";
        int price = 1000;
        int amount = 10;
        String content = "content";
        LocalDate buydate = LocalDate.now();

        BuySaveRequestDto requestDto = BuySaveRequestDto.builder()
                .name(name)
                .price(price)
                .amount(amount)
                .content(content)
                .buydate(buydate)
                .build();

        String url = "http://localhost:" + port + "/manage/buy";

        //when
        ResponseEntity<Long> responseEntity = restTemplate.postForEntity(url, requestDto, Long.class);

        //then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isGreaterThan(0L);

        List<Buy> all = buyRepository.findAll();
        Buy buy = all.get(0);
        assertThat(buy.getAmount()).isEqualTo(amount);
        assertThat(buy.getName()).isEqualTo(name);
        assertThat(buy.getContent()).isEqualTo(content);

    }

}
