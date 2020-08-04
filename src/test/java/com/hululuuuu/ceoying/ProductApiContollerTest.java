package com.hululuuuu.ceoying;

import com.hululuuuu.ceoying.domain.product.Product;
import com.hululuuuu.ceoying.domain.product.ProductRepository;
import com.hululuuuu.ceoying.web.dto.product.ProductSaveRequestDto;
import com.hululuuuu.ceoying.web.dto.product.ProductUpdateRequestDto;
import com.hululuuuu.ceoying.web.dto.sell.SellUpdateRequestDto;
import org.junit.After;
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

import static org.assertj.core.api.Assertions.assertThat;

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

    @After
    public void tearDown() {
        productRepository.deleteAll();
    }

    @Test
    public void 상품_저장된다() {


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
        ResponseEntity<Long> responseEntity = restTemplate.postForEntity(url, requestDto, Long.class);

        //then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isGreaterThan(0L);

        List<Product> productList = productRepository.findAll();
        Product product = productList.get(0);
        assertThat(product.getName()).isEqualTo(name);
        assertThat(product.getPrice()).isEqualTo(price);
        assertThat(product.getAmount()).isEqualTo(amount);
    }

    @Test
    public void 상품_수정된다() {

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
        ResponseEntity<Long> responseEntity = restTemplate.exchange(url, HttpMethod.PUT, requestEntity, Long.class);

        //then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isGreaterThan(0L);

        List<Product> productList = productRepository.findAll();
        Product one = productList.get(0);
        assertThat(one.getName()).isEqualTo(expertedName);
        assertThat(one.getPrice()).isEqualTo(expertedPrice);
        assertThat(one.getAmount()).isEqualTo(expertedAmount);

    }

}
