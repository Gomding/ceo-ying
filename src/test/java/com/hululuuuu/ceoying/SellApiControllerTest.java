package com.hululuuuu.ceoying;

import com.hululuuuu.ceoying.domain.product.Product;
import com.hululuuuu.ceoying.domain.product.ProductRepository;
import com.hululuuuu.ceoying.domain.sell.Sell;
import com.hululuuuu.ceoying.domain.sell.SellRepository;
import com.hululuuuu.ceoying.domain.wallet.Wallet;
import com.hululuuuu.ceoying.domain.wallet.WalletRepository;
import com.hululuuuu.ceoying.web.dto.sell.SellSaveRequestDto;
import com.hululuuuu.ceoying.web.dto.sell.SellUpdateRequestDto;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SellApiControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private SellRepository sellRepository;

    @Autowired
    private ProductRepository productRepository;

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

    @Before
    public void saveProduct() throws Exception {
        productRepository.save(Product.builder()
                .name("치약")
                .price(6500)
                .amount(10)
                .costprice(6300)
                .sellByDate(LocalDate.now())
                .build());
    }

    @After
    public void tearDown() throws Exception {
        sellRepository.deleteAll();
        walletRepository.deleteAll();
        productRepository.deleteAll();
    }

    @Test
    public void 판매내역_저장된다() throws Exception{
        //given
        String name = "김씨";
        String product = "치약";
        int price = 13000;
        int amount = 4;
        String methodOfPayment = "카드결제";
        int profit = 9;
        LocalDate selldate = LocalDate.now();

        SellSaveRequestDto requestDto = SellSaveRequestDto.builder()
                .name(name)
                .product(product)
                .price(price)
                .amount(amount)
                .methodOfPayment(methodOfPayment)
                .profit(profit)
                .selldate(selldate)
                .build();

        String url = "http://localhost:" + port + "/manage/sells";

        //when
        ResponseEntity<Long> responseEntity = restTemplate.postForEntity(url, requestDto, Long.class);

        //then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isGreaterThan(0L);

        List<Sell> all = sellRepository.findAll();
        Sell sell = all.get(0);
        assertThat(sell.getAmount()).isEqualTo(amount);
        assertThat(sell.getName()).isEqualTo(name);
        assertThat(sell.getMethodOfPayment()).isEqualTo(methodOfPayment);

    }

    @Test
    public void 판매내역_수정된다() {
        //given
        String name = "김씨";
        String product = "치약";
        int price = 13000;
        int amount = 4;
        String methodOfPayment = "카드결제";
        int profit = 9;
        LocalDate selldate = LocalDate.now();

        Sell saveSell = sellRepository.save(Sell.builder()
                .name(name)
                .product(product)
                .price(price)
                .amount(amount)
                .methodOfPayment(methodOfPayment)
                .profit(profit)
                .selldate(selldate)
                .build());

        Long updatedId = saveSell.getId();
        String expertedName = "박씨";
        int expectedAmount = 5;
        String expertedMethodOfPayment = "무통장입금";

        SellUpdateRequestDto requestDto = SellUpdateRequestDto.builder()
                .name(expertedName)
                .product(product)
                .price(price)
                .amount(expectedAmount)
                .methodOfPayment(expertedMethodOfPayment)
                .profit(price)
                .selldate(selldate)
                .build();

        String url = "http://localhost:" + port + "/manage/sells/" + updatedId;

        HttpEntity<SellUpdateRequestDto> requestEntity = new HttpEntity<>(requestDto);

        //when
        ResponseEntity<Long> responseEntity = restTemplate.exchange(url, HttpMethod.PUT, requestEntity, Long.class);

        //then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isGreaterThan(0L);

        List<Sell> all = sellRepository.findAll();
        Sell sell = all.get(0);
        assertThat(sell.getAmount()).isEqualTo(expectedAmount);
        assertThat(sell.getName()).isEqualTo(expertedName);
        assertThat(sell.getMethodOfPayment()).isEqualTo(expertedMethodOfPayment);


    }

}
