package com.hululuuuu.ceoying.web.dto.buy;

import com.hululuuuu.ceoying.domain.yiying.Buy;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@NoArgsConstructor
@Getter
public class BuyResponseDto {

    private Long id;
    private String name;  // 구매한 상품의 이름
    private int price;   // 구매한 상품의 가격
    private int amount;   // 구매한 물품 수량
    private String content;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate buydate;

    public BuyResponseDto(Buy entity) {
        this.id = entity.getId();
        this.name = entity.getName();
        this.price = entity.getPrice();
        this.amount = entity.getAmount();
        this.content = entity.getContent();
        this.buydate = entity.getBuydate();
    }


}
