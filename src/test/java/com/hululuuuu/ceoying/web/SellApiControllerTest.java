package com.hululuuuu.ceoying.web;

import com.fasterxml.jackson.databind.ObjectMapper;
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
import org.springframework.http.*;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

    @Autowired
    private WebApplicationContext context;

    private MockMvc mvc;

    @Before
    public void setup() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }


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
    @WithMockUser(roles = "ADMIN")
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
        mvc.perform(post(url)
        .contentType(MediaType.APPLICATION_JSON_UTF8)
        .content(new ObjectMapper().writeValueAsString(requestDto)))
                .andExpect(status().isOk());


        //then
        List<Sell> all = sellRepository.findAll();
        Sell sell = all.get(0);
        Wallet wallet = walletRepository.findAll().get(1);
        assertThat(sell.getAmount()).isEqualTo(amount);
        assertThat(sell.getName()).isEqualTo(name);
        assertThat(sell.getMethodOfPayment()).isEqualTo(methodOfPayment);
        assertThat(wallet.getStatementDate()).isEqualTo(LocalDate.now());
        assertThat(wallet.getStatement()).isEqualTo("+ " + profit + "원");

    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void 판매내역_수정된다() throws Exception{
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
        int expectedPrice = 30000;
        String expertedMethodOfPayment = "무통장입금";

        SellUpdateRequestDto requestDto = SellUpdateRequestDto.builder()
                .name(expertedName)
                .product(product)
                .price(expectedPrice)
                .amount(expectedAmount)
                .methodOfPayment(expertedMethodOfPayment)
                .profit(price)
                .selldate(selldate)
                .build();

        String url = "http://localhost:" + port + "/manage/sells/" + updatedId;

        //when
        mvc.perform(put(url)
        .contentType(MediaType.APPLICATION_JSON_UTF8)
        .content(new ObjectMapper().writeValueAsString(requestDto)))
                .andExpect(status().isOk());

        //then
        List<Sell> all = sellRepository.findAll();
        Sell sell = all.get(0);
        Wallet wallet = walletRepository.findAll().get(1);
        assertThat(sell.getAmount()).isEqualTo(expectedAmount);
        assertThat(sell.getName()).isEqualTo(expertedName);
        assertThat(sell.getMethodOfPayment()).isEqualTo(expertedMethodOfPayment);
        assertThat(wallet.getStatementDate()).isEqualTo(LocalDate.now());
        assertThat(wallet.getRecord()).isEqualTo(product + " 판매 수정");


    }

}
