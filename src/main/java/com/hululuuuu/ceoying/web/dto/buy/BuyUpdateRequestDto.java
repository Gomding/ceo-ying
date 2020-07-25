package com.hululuuuu.ceoying.web.dto.buy;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@NoArgsConstructor
@Getter
public class BuyUpdateRequestDto {

    private String name;  // 구매한 상품의 이름
    private int price;   // 구매한 상품의 가격
    private int amount;   // 구매한 물품 수량
    private String content;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate buydate;

    @Builder
    public BuyUpdateRequestDto(String name, int price, int amount, String content, LocalDate buydate) {
        this.name = name;
        this.price = price;
        this.amount = amount;
        this.content = content;
        this.buydate = buydate;
    }
}
