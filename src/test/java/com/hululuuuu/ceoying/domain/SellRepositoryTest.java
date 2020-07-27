package com.hululuuuu.ceoying.domain;

import com.hululuuuu.ceoying.domain.sell.Sell;
import com.hululuuuu.ceoying.domain.sell.SellRepository;
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
public class SellRepositoryTest {

    @Autowired
    private SellRepository sellRepository;

    @After
    public void cleanUp() {
        sellRepository.deleteAll();
    }

    @Test
    public void 판매글저장_불러오기() {

        //given
        String name = "김씨";
        String product = "치약";
        int price = 100;
        int amount = 10;
        String methodOfPayment = "카드결제";
        int profit = 9;
        LocalDate selldate = LocalDate.now();

        sellRepository.save(Sell.builder()
                    .name(name)
                    .product(product)
                    .price(price)
                    .amount(amount)
                    .methodOfPayment(methodOfPayment)
                    .profit(profit)
                    .selldate(selldate)
                    .build());


        //when
        List<Sell> all = sellRepository.findAll();

        //then
        Sell sell = all.get(0);
        assertThat(sell.getName()).isEqualTo(name);
        assertThat(sell.getAmount()).isEqualTo(amount);

    }
}
