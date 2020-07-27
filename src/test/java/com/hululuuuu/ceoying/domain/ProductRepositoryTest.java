package com.hululuuuu.ceoying.domain;

import com.hululuuuu.ceoying.domain.product.Product;
import com.hululuuuu.ceoying.domain.product.ProductRepository;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductRepositoryTest {

    @Autowired
    ProductRepository productRepository;

    @After
    public void cleanUp() {
        productRepository.deleteAll();
    }

    @Test
    public void 구매글저장_불러오기() {
        //given
        String name = "name";
        int price = 1000;
        int amount = 10;
        int costPrice = 888;
        LocalDate sellByDate = LocalDate.now();

        productRepository.save(Product.builder()
                .name(name)
                .price(price)
                .amount(amount)
                .costprice(costPrice)
                .sellByDate(sellByDate)
                .build());

        //when
        List<Product> productList = productRepository.findAll();

        //then
        Product product = productList.get(0);
        assertThat(product.getName()).isEqualTo(name);
        assertThat(product.getAmount()).isEqualTo(amount);


    }

}

