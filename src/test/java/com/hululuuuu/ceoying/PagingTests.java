package com.hululuuuu.ceoying;

import com.hululuuuu.ceoying.domain.yiying.Buy;
import com.hululuuuu.ceoying.domain.yiying.BuyRepository;
import com.hululuuuu.ceoying.myComponent.PageableDefault;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PagingTests {

    @Autowired
    private BuyRepository buyRepository;

    @After
    public void tear_down() {
        buyRepository.deleteAll();
    }

    @Test
    public void 페이징_생성됐다() {

        for (int i = 0; i < 100; i++) {
            buyRepository.save(Buy.builder()
            .name(i + "번째 제품")
            .amount(i)
            .price(i * 1000)
            .content(i + "번째 구")
            .buydate(LocalDate.now())
            .build());
        }


    }

}
