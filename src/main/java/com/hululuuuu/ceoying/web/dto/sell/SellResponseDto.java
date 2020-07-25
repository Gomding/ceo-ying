package com.hululuuuu.ceoying.web.dto.sell;

import com.hululuuuu.ceoying.domain.sell.Sell;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
@Getter
public class SellResponseDto {

    private String name;        // 구매자
    private String product;      // 판매 상품
    private int amount;         // 판매 수량
    private int price;          // 판매 금액
    private String methodOfPayment;         // 결제수단
    private int profit;         // 수익
    LocalDate selldate;         // 판매 날짜

    public SellResponseDto(Sell entity) {
        this.name = entity.getName();
        this.product = entity.getProduct();
        this.amount = entity.getAmount();
        this.price = entity.getPrice();
        this.methodOfPayment = entity.getMethodOfPayment();
        this.profit = entity.getProfit();
        this.selldate = entity.getSelldate();
    }

}
