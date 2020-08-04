package com.hululuuuu.ceoying;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hululuuuu.ceoying.domain.product.Product;
import com.hululuuuu.ceoying.domain.product.ProductRepository;
import com.hululuuuu.ceoying.web.dto.product.ProductSaveRequestDto;
import com.hululuuuu.ceoying.web.dto.product.ProductUpdateRequestDto;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProductApiContollerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private ProductRepository productRepository;

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

    @After
    public void tearDown() {
        productRepository.deleteAll();
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void 상품_저장된다() throws Exception{


        //given
        String name = "name";
        int price = 1000;
        int amount = 10;
        int costPrice = 888;
        LocalDate sellByDate = LocalDate.now();

        ProductSaveRequestDto requestDto = ProductSaveRequestDto.builder()
                .name(name)
                .price(price)
                .amount(amount)
                .costprice(costPrice)
                .sellByDate(sellByDate)
                .build();

        String url = "http://localhost:" + port + "/manage/products";

        //when
        mvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(new ObjectMapper().writeValueAsString(requestDto)))
                .andExpect(status().isOk());

        List<Product> productList = productRepository.findAll();
        Product product = productList.get(0);
        assertThat(product.getName()).isEqualTo(name);
        assertThat(product.getPrice()).isEqualTo(price);
        assertThat(product.getAmount()).isEqualTo(amount);
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void 상품_수정된다() throws Exception{

        //given
        String name = "김씨";
        int price = 1000;
        int amount = 10;
        int costPrice = 888;
        LocalDate sellByDate = LocalDate.now();

        Product product = productRepository.save(Product.builder()
                .name(name)
                .price(price)
                .amount(amount)
                .costprice(costPrice)
                .sellByDate(sellByDate)
                .build());

        Long updatedId = product.getId();
        String expertedName = "박씨";
        int expertedPrice = 500;
        int expertedAmount = 5;
        int expertedCostPrice = 777;

        ProductUpdateRequestDto requestDto = ProductUpdateRequestDto.builder()
                .name(expertedName)
                .amount(expertedAmount)
                .price(expertedPrice)
                .costprice(expertedCostPrice)
                .sellByDate(sellByDate)
                .build();

        String url = "http://localhost:" + port + "/manage/products/" + updatedId;

        HttpEntity<ProductUpdateRequestDto> requestEntity = new HttpEntity<>(requestDto);

        //when
        mvc.perform(put(url)
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .content(new ObjectMapper().writeValueAsString(requestDto)))
                .andExpect(status().isOk());

        //then
        List<Product> productList = productRepository.findAll();
        Product one = productList.get(0);
        assertThat(one.getName()).isEqualTo(expertedName);
        assertThat(one.getPrice()).isEqualTo(expertedPrice);
        assertThat(one.getAmount()).isEqualTo(expertedAmount);

    }

}
