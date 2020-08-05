package com.hululuuuu.ceoying.web.dto.buy;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.hululuuuu.ceoying.domain.yiying.Buy;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@NoArgsConstructor
@Getter
public class BuySaveRequestDto {

    private String name;  // 구매한 상품의 이름
    private int price;   // 구매한 상품의 가격
    private int amount;   // 구매한 물품 수량
    private String content;

    @JsonSerialize(using = ToStringSerializer.class)
    private LocalDate buydate;

    @Builder
    public BuySaveRequestDto(String name, int price, int amount, String content, LocalDate buydate) {
        this.name = name;
        this.price = price;
        this.amount = amount;
        this.content = content;
        this.buydate = buydate;
    }

    public Buy toEntity() {
        return Buy.builder()
                .name(name)
                .price(price)
                .amount(amount)
                .content(content)
                .buydate(buydate)
                .build();
    }

}
