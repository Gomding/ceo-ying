package com.hululuuuu.ceoying.domain;

import com.hululuuuu.ceoying.domain.yiying.Buy;
import com.hululuuuu.ceoying.domain.yiying.BuyRepository;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BuyRepositoryTest {

    @Autowired
    BuyRepository buyRepository;

    @After
    public void cleanUp() {
        buyRepository.deleteAll();
    }

    @Test
    public void 구매글저장_불러오기() {
        //given
        String name = "name";
        int price = 1000;
        int amount = 10;
        String content = "content";
        LocalDate buydate = LocalDate.now();

        buyRepository.save(Buy.builder()
                .name(name)
                .price(price)
                .amount(amount)
                .content(content)
                .buydate(buydate)
                .build());

        //when
        List<Buy> buyList = buyRepository.findAll();

        //then
        Buy buy = buyList.get(0);
        assertThat(buy.getName()).isEqualTo(name);
        assertThat(buy.getAmount()).isEqualTo(amount);
    }
}
