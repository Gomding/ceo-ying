package com.hululuuuu.ceoying.web.dto.product;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@NoArgsConstructor
@Getter
public class ProductUpdateRequestDto {

    private String name; // 제품명
    private int amount;  // 제품 재고
    private int price;   // 제품 가격
    private int costprice;   // 제품 원가

    @JsonSerialize(using = ToStringSerializer.class)
    private LocalDate sellByDate;

    @Builder
    public ProductUpdateRequestDto(String name, int amount, int price, int costprice, LocalDate sellByDate) {
        this.name = name;
        this.amount = amount;
        this.price = price;
        this.costprice = costprice;
        this.sellByDate = sellByDate;
    }
}
