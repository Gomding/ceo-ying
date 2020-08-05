package com.hululuuuu.ceoying.web.dto.sell;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.hululuuuu.ceoying.domain.sell.Sell;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@NoArgsConstructor
@Getter
public class SellSaveRequestDto {

    private String name;        // 구매자
    private String product;      // 판매 상품
    private int amount;         // 판매 수량
    private int price;          // 판매 금액
    private String methodOfPayment;         // 결제수단
    private int profit;         // 수익

    @JsonSerialize(using = ToStringSerializer.class)
    private LocalDate selldate; // 판매 날짜

    @Builder
    public SellSaveRequestDto(String name, String product, int amount, int price, String methodOfPayment, int profit, LocalDate selldate) {
        this.name = name;
        this.product = product;
        this.amount = amount;
        this.price = price;
        this.methodOfPayment = methodOfPayment;
        this.profit = profit;
        this.selldate = selldate;
    }

    public Sell toEntity() {
        return Sell.builder()
                .name(name)
                .product(product)
                .amount(amount)
                .price(price)
                .methodOfPayment(methodOfPayment)
                .profit(profit)
                .selldate(selldate)
                .build();
    }

}
